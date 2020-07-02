package com.example.demo.services;


import com.example.demo.utils.QueryUtil;
import org.springframework.stereotype.Service;

@Service
public class FormulaOneTeamsService {

    public String getListOfTeams(Integer limit, String language) {

        String query = "SELECT ?subject ?name (SAMPLE(?thumbnail) AS ?picture) (SAMPLE(?description) AS ?description)  (SAMPLE(?webPageLink) AS ?webLink) (SAMPLE(?constructorName) AS ?constructorName) (SAMPLE(?wins) AS ?wins)\n" +
                "WHERE {\n" +
                "?subject rdf:type dbo:FormulaOneTeam .\n" +
                "?subject rdfs:label ?name .\n" +
                "?subject dbp:poles ?poles.\n" +
                "?subject dbp:wins ?wins .\n" +
                "OPTIONAL {?subject dbo:thumbnail ?thumbnail .}\n" +
                "OPTIONAL {?subject rdfs:comment ?description .}\n" +
                "OPTIONAL {?subject foaf:homepage ?webPageLink.}\n" +
                "OPTIONAL {?subject dbp:constructorName ?constructorName .}\n" +
                "\n" +
                "FILTER(lang(?name) = \"" + language + "\" && lang(?description)=\"" + language + "\")\n" +
                "}\n" +
                "LIMIT " + limit.toString();
        return QueryUtil.getResultFromQuery(query);
    }

    public String search(String name,String language,Integer limit) {

        String query = "SELECT ?subject (SAMPLE(?name) AS ?name) (SAMPLE(?thumbnail) AS ?picture) (SAMPLE(?description) AS ?description)  (SAMPLE(?webPageLink) AS ?webLink) (SAMPLE(?constructorName) AS ?constructorName) (SAMPLE(?wins) AS ?wins)\n" +
                "WHERE {\n" +
                "?subject rdf:type dbo:FormulaOneTeam .\n" +
                "?subject rdfs:label ?name .\n" +
                "?subject dbp:poles ?poles .\n" +
                "?subject dbp:wins ?wins .\n" +
                "OPTIONAL {?subject foaf:homepage ?webPageLink.}\n" +
                "OPTIONAL {?subject dbo:thumbnail ?thumbnail .}\n" +
                "OPTIONAL {?subject rdfs:comment ?description .}\n" +
                "OPTIONAL {?subject dbp:constructorName ?constructorName .}\n" +
                "FILTER(lang(?name) = \"en\" &&lang(?description) = \"" + language + "\" && contains(lcase(str(?name)),lcase(\"" + name + "\")))}\n";

        return QueryUtil.getResultFromQuery(query);
    }
}
