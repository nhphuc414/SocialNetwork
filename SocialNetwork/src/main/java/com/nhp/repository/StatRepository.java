/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ad
 */
public interface StatRepository {
    public List<Object[]> statsRevenue(Map<String, String> params) throws ParseException;
    
}
