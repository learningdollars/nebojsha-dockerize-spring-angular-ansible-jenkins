package com.example.demo.services;

import com.example.demo.utils.QueryUtil;
import org.springframework.stereotype.Service;

@Service
public class FormulaOneService {

    public String getAbstract(String language) {
        String query = "select dbr:Formula_One as ?subject ?abstract where {dbr:Formula_One dbo:abstract ?abstract. filter (lang (?abstract) = \"" + language + "\")}";
        return QueryUtil.getResultFromQuery(query);
    }
}

