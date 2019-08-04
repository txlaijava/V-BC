package com.shopping.base.repository.bc;

import com.shopping.base.domain.bc.BcReserveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @anthor bin
 * @data 2019/7/10 14:24
 */
public interface BcReserveRecordRepository extends JpaRepository<BcReserveRecord,Long>{
    /**
     * 根据用户id和appid查询预约就餐记录
     * @param bcUserId
     * @param appId
     * @return
     */
    @Query("select obj from BcReserveRecord obj where obj.appId=:appId and obj.bcUserId=:bcUserId order by obj.reserveTime desc")
    List<BcReserveRecord> getBcReserveRecordList(@Param("bcUserId") Long bcUserId,@Param("appId") String appId);

    /**
     * 根据选中的记录删除数据库的预约记录
     * @param appId
     * @param bcUserId
     * @param id
     * @return
     */
    @Modifying
    @Query("delete from BcReserveRecord obj where obj.appId=:appId and obj.bcUserId=:bcUserId and obj.id =:id")
    Integer  deleteBcReserveRecordByAppIdAndBcUserIdAndReserveTime(@Param("appId") String appId,@Param("bcUserId") Long bcUserId,@Param("id") Long id);

    /**
     * 根据预约时间获取当天没有报餐的预约记录
     * @param appId
     * @param reserveTime
     * @return
     */
    @Query("select obj from  BcReserveRecord  obj where obj.appId=:appId and obj.reserveTime like concat('%',:reserveTime,'%')  and obj.bcUserId not in (select r.userId from BcRecord r where r.appId =:appId and r.dinTime like concat('%',:reserveTime,'%'))")
    List<BcReserveRecord>  getBcReserveRecordListByReserveTime(@Param("appId")String appId,@Param("reserveTime")String reserveTime);

    /**
     * 根据预约时间删除预记录
     * @param reserveTIme
     * @param appId
     * @return
     */
    @Modifying
    @Query("delete from BcReserveRecord  where reserveTime =:reserveTime and appId = :appId")
    Integer deleteAllByReserveTime(@Param("reserveTime")Date reserveTIme,@Param("appId")String appId);

    /**
     * 根据传来的年月查询数据库与之相匹配的预约记录
     * @param appId
     * @param reserveTime
     * @return
     */
    @Query("select obj from BcReserveRecord obj where obj.appId=:appId and obj.bcUserId=:bcUserId and obj.reserveTime like concat('%',:reserveTime,'%') ")
    List<BcReserveRecord> getBcReserveRecordByCurYearAndMonth(@Param("appId")String appId,@Param("bcUserId") Long bcUserId,@Param("reserveTime")String reserveTime);
}
