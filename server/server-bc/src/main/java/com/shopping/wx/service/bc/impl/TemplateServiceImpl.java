package com.shopping.wx.service.bc.impl;

import com.shopping.base.domain.other.Template;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.wx.service.bc.ITemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="templateServiceImpl")
@Transactional
public class TemplateServiceImpl extends BaseServiceImpl<Template,Long> implements ITemplateService {
}
