package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.StockMapper;
import com.hchenpan.pojo.Applytransfer;
import com.hchenpan.pojo.CancellingstocksWZ;
import com.hchenpan.pojo.Stock;
import com.hchenpan.pojo.Transferlist;
import com.hchenpan.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.StockServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("stockService")
public class StockServiceImpl extends BaseServiceImpl<StockMapper, Stock> implements StockService {
    private final StockMapper mapper;

    @Autowired
    public StockServiceImpl(StockMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public void Cutkcnumber(String dcckcode, String wzcode, String realnum) {

    }

    @Override
    public void Addkcnumber(Transferlist transferlist, Applytransfer old) {

    }

    @Override
    public int getwznum(String wzcode, String stockcode) {
        return selectCount(new EntityWrapper<Stock>()
                .eq("wzcode", wzcode)
                .eq("stockcode", stockcode)
                .isNull("tranflag")
                .isNull("stockyearmon")
        );
    }

    @Override
    public List<Stock> getwzlist(String stockcode, String wzcode) {

        return selectList(new EntityWrapper<Stock>()
                .eq("wzcode", wzcode)
                .eq("stockcode", stockcode)
                .isNull("tranflag")
                .isNull("stockyearmon")


        );
    }

    @Override
    public String getkcnum(String wzcode, String stockcode) {

        return selectOne(new EntityWrapper<Stock>()
                .eq("wzcode", wzcode)
                .eq("stockcode", stockcode)
                .isNull("tranflag")
                .isNull("stockyearmon")
        ).getBqend();
    }

    @Override
    public String getprice(String wzcode, String stockcode) {

        return selectOne(new EntityWrapper<Stock>()
                .eq("wzcode", wzcode)
                .eq("stockcode", stockcode)
                .isNull("tranflag")
                .isNull("stockyearmon")
        ).getPrice();
    }

    @Override
    public void TKstocks(CancellingstocksWZ cw) {
        Stock stock1 = selectOne(new EntityWrapper<Stock>()
                .eq("wzcode", cw.getWzcode())
                .eq("stockcode", cw.getStockcode())
                .isNull("tranflag")
                .isNull("stockyearmon")
        );
        if (stock1 != null) {
            BigDecimal bqend = new BigDecimal(stock1.getBqend()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal bqin = new BigDecimal(stock1.getBqin()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal bqinmoney = new BigDecimal(stock1.getBqinmoney()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal bqendmoney = new BigDecimal(stock1.getBqendmoney()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal fhnum = new BigDecimal(cw.getTksum()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal fhprice = new BigDecimal(cw.getTkprice()).setScale(2, BigDecimal.ROUND_HALF_UP);

            BigDecimal price = (bqendmoney.add(fhnum.multiply(fhprice))).divide((fhnum.add(bqend)), 2,
                    BigDecimal.ROUND_HALF_UP);

            bqin = bqin.add(fhnum).setScale(2, BigDecimal.ROUND_HALF_UP);

            bqinmoney = bqinmoney.add((fhnum).multiply(fhprice)).setScale(2, BigDecimal.ROUND_HALF_UP);

            bqendmoney = bqendmoney.add((fhnum).multiply(fhprice)).setScale(2, BigDecimal.ROUND_HALF_UP);

            bqend = bqend.add(fhnum).setScale(2, BigDecimal.ROUND_HALF_UP);

            stock1.setBqin(bqin.toString());
            stock1.setBqinmoney(bqinmoney.toString());
            stock1.setBqendmoney(bqendmoney.toString());
            stock1.setBqend(bqend.toString());
            stock1.setPrice(price.toString());

            updateById(stock1);
        }
    }
}