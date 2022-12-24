package com.example.demo.mapper.ms;

import com.example.demo.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapperMs {

    /**
     * MySQL上で指定した生年月日の上司をもつEmployeeテーブルのデータを取得する(WITH句)
     * @param birthday 上司の生年月日(yyyy/MM/dd形式)
     * @return Employeeテーブルのリスト
     */
    List<Employee> findByBossBirthdayWith(String birthday);

}
