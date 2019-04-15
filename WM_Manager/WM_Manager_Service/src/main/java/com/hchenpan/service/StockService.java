package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Applytransfer;
import com.hchenpan.pojo.CancellingstocksWZ;
import com.hchenpan.pojo.Stock;
import com.hchenpan.pojo.Transferlist;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.StockService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface StockService extends BaseService<Stock> {

    void Cutkcnumber(String dcckcode, String wzcode, String realnum);

    void Addkcnumber(Transferlist transferlist, Applytransfer old);

    int getwznum(String wzcode, String stockcode);

    List<Stock> getwzlist(String stockcode, String wzcode);

    String getkcnum(String wzcode, String stockcode);

    String getprice(String wzcode, String stockcode);

    void TKstocks(CancellingstocksWZ cw);
}
