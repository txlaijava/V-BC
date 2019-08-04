package com.shopping.base.foundation.data;


import com.shopping.base.foundation.data.annotation.Condition;
import com.shopping.base.foundation.data.annotation.MatchMode;
import com.shopping.base.foundation.form.BaseQueryForm;
import com.shopping.base.foundation.form.PaginationForm;
import com.shopping.base.foundation.util.DateUtils;
import org.assertj.core.util.Arrays;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 查询表单辅助类
 * 
 *
 *
 */
public class QueryFormHelper {

	private static final ConversionService conversionService = BaseDao.getConversionService();

	public static <T> QueryWrapper createQueryWrapper(final BaseQueryForm form) {
		return new QueryWrapper() {
			@Override
			public void wrap(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, boolean sort) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				addSearch(form, predicates, root, cb);
				addFields(form, predicates, root, cb);
				Predicate[] array = new Predicate[predicates.size()];
				predicates.toArray(array);
				query.where(array);
				// 加入排序
				if (sort) {
					List<Order> orders = getOrdes(form, root);
					query.orderBy(orders);
				}
			}
		};
	}

	public static <T> Specification<T> createSpecification(final PaginationForm form) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				addSearch(form, predicates, root, cb);
				addFields(form, predicates, root, cb);
				Predicate[] array = new Predicate[predicates.size()];
				predicates.toArray(array);
				query.where(array);
				// 加入排序
				List<Order> orders = getOrdes(form, root);
				query.orderBy(orders);
				return null;
			}
		};
	}

	public static Pageable createPageable(PaginationForm form) {
		int currentPage = form.getCurrentPage() - 1;
		int pageSize = form.getPageSize();
		PageRequest pageable = new PageRequest(currentPage, pageSize);
		return pageable;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> void addFields(final BaseQueryForm form, final List<Predicate> predicates, final Root<T> root,
			final CriteriaBuilder cb) {

		ReflectionUtils.doWithLocalFields(form.getClass(), new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				Condition condition = field.getAnnotation(Condition.class);
				if (condition == null) {
					return;
				}
				String pathName = condition.path();
				if (StringUtils.isEmpty(pathName)) {
					pathName = field.getName();
				}
				ReflectionUtils.makeAccessible(field);
				Object value = field.get(form);
				if (StringUtils.isEmpty(value)) {
					return;
				}
				if (value instanceof Date) {
					if (condition.minTime()) {
						value = DateUtils.getBeginTimeOfDay((Date) value);
					}
					if (condition.maxTime()) {
						value = DateUtils.getEndTimeOfDay((Date) value);
					}
				}
				if (value instanceof String) {
					value = ((String) value).trim();
				}
				Class<?> valueType = condition.type();
				MatchMode mode = condition.match();
				if (mode == MatchMode.CONTAINS) {
					Expression<String> path = getPath(root, pathName);
					Predicate predicate = cb.like(path, "%" + value + "%");
					predicates.add(predicate);
				} else if (mode == MatchMode.START) {
					Expression<String> path = getPath(root, pathName);
					Predicate predicate = cb.like(path, "%" + value);
					predicates.add(predicate);
				} else if (mode == MatchMode.END) {
					Expression<String> path = getPath(root, pathName);
					Predicate predicate = cb.like(path, value + "%");
					predicates.add(predicate);
				} else if (mode == MatchMode.BETWEEN) {
					Object[] arrayValues = null;
					if (Arrays.isArray(value)) {
						arrayValues = (Object[]) value;
					} else {
						arrayValues = value.toString().split(",");
					}
					if (arrayValues.length > 1) {
						Expression<Comparable> path = getPath(root, pathName);
						Comparable value1 = (Comparable) conversionService.convert(arrayValues[0], valueType);
						Comparable value2 = (Comparable) conversionService.convert(arrayValues[1], valueType);
						Predicate predicate = cb.between(path, value1, value2);
						predicates.add(predicate);
					}
				} else if (mode == MatchMode.IN) {
					Object array;
					if (Arrays.isArray(value)) {
						array = value;
					} else {
						array = value.toString().split(",");
					}
					int length = Array.getLength(array);
					if (length > 0) {
						In<Object> predicate = cb.in(getPath(root, pathName));
						for (int i = 0; i < length; i++) {
							Object val = Array.get(array, i);
							val = conversionService.convert(val, valueType);
							predicate.value(val);
						}
						predicates.add(predicate);
					} else {
						predicates.add(cb.isTrue(cb.or()));
					}
				} else if (mode == MatchMode.NIN) {
					Object array;
					if (Arrays.isArray(value)) {
						array = value;
					} else {
						array = value.toString().split(",");
					}
					int length = Array.getLength(array);
					if (length > 0) {
						In<Object> predicate = cb.in(getPath(root, pathName));
						for (int i = 0; i < length; i++) {
							Object val = Array.get(array, i);
							val = conversionService.convert(val, valueType);
							predicate.value(val);
						}
						predicates.add(cb.not(predicate));
					}
				} else if (mode == MatchMode.GE) {
					Expression<Comparable> path = getPath(root, pathName);
					Comparable num = (Comparable) conversionService.convert(value, valueType);
					Predicate predicate = cb.greaterThanOrEqualTo(path, num);
					predicates.add(predicate);
				} else if (mode == MatchMode.LE) {
					Expression<Comparable> path = getPath(root, pathName);
					Comparable num = (Comparable) conversionService.convert(value, valueType);
					Predicate predicate = cb.lessThanOrEqualTo(path, num);
					predicates.add(predicate);
				} else if (mode == MatchMode.GT) {
					Expression<Comparable> path = getPath(root, pathName);
					Comparable num = (Comparable) conversionService.convert(value, valueType);
					Predicate predicate = cb.greaterThan(path, num);
					predicates.add(predicate);
				} else if (mode == MatchMode.LT) {
					Expression<Comparable> path = getPath(root, pathName);
					Comparable num = (Comparable) conversionService.convert(value, valueType);
					Predicate predicate = cb.lessThan(path, num);
					predicates.add(predicate);
				} else if (mode == MatchMode.NE) {
					Number num = (Number) conversionService.convert(value, valueType);
					Predicate predicate = cb.notEqual(getPath(root, pathName), num);
					predicates.add(predicate);
				} else if (mode == MatchMode.ISNULL) {
					if ((boolean) value) {
						Predicate predicate = cb.isNull(getPath(root, pathName));
						predicates.add(predicate);
					}
				} else {
					value = conversionService.convert(value, valueType);
					Predicate predicate = cb.equal(getPath(root, pathName), value);
					predicates.add(predicate);
				}
			}
		});
	}

	/**
	 * 加入搜索条件
	 * 
	 * @param predicates 条件组合
	 */
	private static <T> void addSearch(BaseQueryForm form, List<Predicate> predicates, final Root<T> root,
			final CriteriaBuilder cb) {
		if (form.getSearchFields() == null) {
			return;
		}
		String searchText = form.getSearchText();
		if (StringUtils.isEmpty(searchText)) {
			return;
		}
		String[] searchFields = form.getSearchFields();
		if (searchFields.length == 0) {
			return;
		}
		int length = searchFields.length;
		Predicate[] predicateGroup = new Predicate[length];
		for (int i = 0; i < length; i++) {
			String field = searchFields[i];
			Expression<String> path = getPath(root, field);
			predicateGroup[i] = cb.like(path, "%" + searchText + "%");
		}
		predicates.add(cb.or(predicateGroup));
	}

	/**
	 * 加入排序依据
	 */
	private static <T> List<Order> getOrdes(BaseQueryForm form, Root<T> root) {
		String orderBy = form.getOrderMapping();
		if (StringUtils.isEmpty(orderBy)) {
			return Collections.emptyList();
		}
		String[] groups = orderBy.trim().split(",");
		List<Order> orders = new ArrayList<Order>(groups.length);
		for (String group : groups) {
			boolean ascending = true;
			String[] array = group.split("\\s", 2);
			String field = array[0];
			if (array.length > 1) {
				ascending = "asc".equals(array[1].toLowerCase());
			}
			Order order = new OrderImpl(getPath(root, field), ascending);
			orders.add(order);
		}
		return orders;
	}

	public static <T> List<Order> getOrdes(String orderBy, Root<T> root) {
		if (StringUtils.isEmpty(orderBy)) {
			return Collections.emptyList();
		}
		String[] groups = orderBy.trim().split(",");
		List<Order> orders = new ArrayList<Order>(groups.length);
		for (String group : groups) {
			boolean ascending = true;
			String[] array = group.split("\\s", 2);
			String field = array[0];
			if (array.length > 1) {
				ascending = "asc".equals(array[0].toLowerCase());
			}
			Order order = new OrderImpl(getPath(root, field), ascending);
			orders.add(order);
		}
		return orders;
	}

	public static <X, T> Expression<X> getPath(Root<T> root, String name) {
		String[] array = name.split("[.]");
		Expression<X> expr = root.get(array[0]);
		for (int i = 1; i < array.length; i++) {
			expr = ((Path<X>) expr).get(array[i]);
		}
		return expr;
	}

	@SuppressWarnings("unchecked")
	public static <T, N extends Number> Expression<N> getExpression(CriteriaBuilder cb, Root<T> root, String input) {
		StringTokenizer tokenizer = new StringTokenizer(input, "+-*/", true);
		Expression<N> expr = getPath(root, tokenizer.nextToken());
		if (tokenizer.hasMoreTokens()) {
			String op = tokenizer.nextToken();
			String name = tokenizer.nextToken();
			Expression<N> expr2 = getPath(root, name);
			if ("+".equals(op)) {
				expr = cb.sum(expr, expr2);
			} else if ("-".equals(op)) {
				expr = cb.diff(expr, expr2);
			} else if ("*".equals(op)) {
				expr = cb.prod(expr, expr2);
			} else if ("/".equals(op)) {
				expr = (Expression<N>) cb.quot(expr, expr2);
			}
		}
		return expr;
	}
}
