///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.nhp.repository.impl;
//
//import com.nhp.pojo.Post;
//import com.nhp.pojo.User;
//import com.nhp.repository.StatRepository;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import org.hibernate.Session;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
///**
// *
// * @author ad
// */
//public class StatRepositoryImpl implements StatRepository {
//    @Autowired
//    private SimpleDateFormat f;
//
//    @Autowired
//    private LocalSessionFactoryBean factory;
//
//    @Override
//    public List<Object[]> statsRevenue(Map<String, String> params) throws ParseException {
//        Session s = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder b = s.getCriteriaBuilder();
//        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
//        
//        Root root = q.from(User.class);
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(b.equal(root.get("confirmed"), "ACCEPT"));
//        String fd = params.get("fromDate");
//        if (fd != null && !fd.isEmpty()) {
//            predicates.add(b.greaterThanOrEqualTo(root.get("createdDate"), f.parse(fd)));
//        }
//        String td = params.get("toDate");
//        if (td != null && !td.isEmpty()) {
//            predicates.add(b.lessThanOrEqualTo(root.get("createdDate"), f.parse(td)));
//        }
//        String quarter = params.get("quarter");
//        if (quarter != null && !quarter.isEmpty()) {
//            String year = params.get("year");
//            if (year != null && !year.isEmpty()) {
//                predicates.add(b.equal(b.function("YEAR", Integer.class, root.get("createdDate")), Integer.valueOf(year)));
//                predicates.add(b.equal(b.function("QUARTER", Integer.class, root.get("createdDate")), Integer.valueOf(quarter)));
//            }
//        }
//        q.select(root.get("id"));
//        q.where(predicates.toArray(Predicate[]::new));
//        Query query = s.createQuery(q);
//        return query.getResultList();
//    }
//
//}
