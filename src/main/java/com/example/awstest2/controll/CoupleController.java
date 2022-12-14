package com.example.awstest2.controll;
import com.example.awstest2.mapper.CoupleMapper;
import com.example.awstest2.mapper.UserMapper;
import com.example.awstest2.model.Couple;
import com.example.awstest2.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoupleController {

    private CoupleMapper mapper;
    public CoupleController(CoupleMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping("/couple")
    public Couple postCouple(@RequestParam("mid") String mid, @RequestParam("did") String did, @RequestParam("sex") String sex) {
        mapper.insertCouple(mid,did);

        Couple couple = new Couple( mapper.selectCouple(did));

        if(sex.equals("M")){
            //아빠일때 엄마 아이디 이름가져옴
            couple.setSpousename(mapper.selectSpouseName(couple.getMid()));
        }else {
            System.out.println("did"+couple.getDid());
            System.out.println("mid"+couple.getMid());
            couple.setSpousename(mapper.selectSpouseName(couple.getDid()));
        }

        return couple;

    }

    @GetMapping("/couple/{id}")
    public Couple getCouple(@PathVariable("id")String id){

        Couple couple = new Couple( mapper.selectCouple(id) );

        if(couple.getCouplenum()!=-1){

            if(couple.getDid().equals(id)){
                //아빠일때 엄마 아이디 이름가져옴
                couple.setSpousename(mapper.selectSpouseName(couple.getMid()));
            }else {
                System.out.println("did"+couple.getDid());
                System.out.println("mid"+couple.getMid());
                couple.setSpousename(mapper.selectSpouseName(couple.getDid()));
            }
        }else
            couple=null;
        //System.out.println(couple.toString());
        return couple;
    }

}
