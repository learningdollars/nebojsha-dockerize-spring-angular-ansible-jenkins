package com.example.demo.services;

import com.example.demo.utils.QueryUtil;
import org.springframework.stereotype.Service;

@Service
public class FormulaOneDriversService {

    public String getListOfDrivers(Integer limit, String language) {
        /*
         * This query is designed to ordering by points but this can easily be changed, it should be discussed!
         *  TODO discuss with team about ordering!
         */
        String query = "select ?subject ?name ?comment ?thumbnail ?points\n" +
                "where {\n" +
                "?subject rdf:type dbo:FormulaOneRacer.\n" +
                "?subject foaf:name ?name.\n" +
                "?subject rdfs:comment ?comment.\n" +
                "?subject dbp:points ?points. \n" +
                "?subject dbo:thumbnail ?thumbnail \n" +
                "filter(lang(?comment) = \"" + language + "\")\n" +
                "}\n" +
                "order by desc(?points)" +
                "limit " + limit.toString();
        return QueryUtil.getResultFromQuery(query);
    }

    public String search(String name,String language,Integer limit){
        String query="select ?subject (SAMPLE(?name) AS ?name) (SAMPLE(?thumbnail) AS ?thumbnail) (SAMPLE(?birthDate) AS ?birthDate) (SAMPLE(?birthPlace)" +
                " AS ?birthPlace) (SAMPLE(?wins) AS ?wins) (SAMPLE(?nationality) AS ?nationality) (SAMPLE(?points) AS ?points) (SAMPLE(?deathDate) AS ?deathDate) " +
                "(SAMPLE(?quote) AS ?quote) (SAMPLE(?comment) AS ?comment)\n" +
                "where {\n" +
                "?subject rdf:type dbo:FormulaOneRacer;\n" +
                "         foaf:name ?name.\n" +
                "    OPTIONAL { ?subject dbp:points ?points.}\n" +
                "    OPTIONAL { ?subject rdfs:comment ?comment.}\n" +
                "    OPTIONAL { ?subject dbo:podiums ?podiums.}\n" +
                "    OPTIONAL { ?subject dbo:thumbnail ?thumbnail.}\n" +
                "    OPTIONAL { ?subject dbo:birthDate ?birthDate.}\n" +
                "    OPTIONAL { ?subject dbo:birthPlace ?birthPlace.}\n" +
                "    OPTIONAL { ?subject dbo:wins ?wins.}\n" +
                "    OPTIONAL { ?subject dbp:nationality ?nationality.}\n" +
                "    OPTIONAL { ?subject dbo:deathDate ?deathDate.}\n" +
                "    OPTIONAL { ?subject dbp:quote ?quote. }\n" +
                "                  \n" +
                "filter(lang(?comment) = \""+language+"\" && contains(lcase(str(?name)),lcase(\""+name+"\")))\n" +
                "}\n" +
                "\n" +
                "group by ?subject\n" +
                "limit "+limit.toString();
        return QueryUtil.getResultFromQuery(query);
    }

    public String getDriverDetails(String fullName, String language) {
        String subject = "<http://dbpedia.org/resource/"+fullName.replace(" ","_")+">";
        String query = "select ?subject ?name (SAMPLE(?thumbnail) AS ?thumbnail) (SAMPLE(?birthDate) AS ?birthDate) (SAMPLE(?birthPlace) AS ?birthPlace) (SAMPLE(?wins) AS ?wins) (SAMPLE(?nationality) AS ?nationality) (SAMPLE(?points) AS ?points) (SAMPLE(?deathDate) AS ?deathDate) (SAMPLE(?quote) AS ?quote) (SAMPLE(?abstract) AS ?abstract)\n" +
                "where{\n" +
                "       Values ?subject {"+subject+"} . " +
                "     ?subject rdf:type dbo:FormulaOneRacer. \n" +
                "     ?subject foaf:name ?name. \n" +
                "     ?subject dbo:abstract ?abstract. \n" +
                "     OPTIONAL { ?subject dbp:points ?points.} \n" +
                "     OPTIONAL { ?subject dbo:podiums ?podiums.} \n" +
                "     OPTIONAL { ?subject dbo:thumbnail ?thumbnail.} \n" +
                "     OPTIONAL { ?subject dbo:birthDate ?birthDate.} \n" +
                "     OPTIONAL { ?subject dbo:birthPlace ?birthPlace.} \n" +
                "     OPTIONAL { ?subject dbo:wins ?wins.} \n" +
                "     OPTIONAL { ?subject dbp:nationality ?nationality.} \n" +
                "     OPTIONAL { ?subject dbo:deathDate ?deathDate.} \n" +
                "     OPTIONAL { ?subject dbp:quote ?quote. } \n" +
                "      \n" +
                "     filter(lang(?abstract) = \""+language+"\") \n" +
                "      } ";
                return "{ \"basic\": "+ QueryUtil.getResultFromQuery(query) + ", \"extended\":" + this.getMoreDriverDetails(fullName)+"}";
    }

    public String getMoreDriverDetails(String fullName) {
        String subject = "<http://dbpedia.org/resource/"+fullName.replace(" ","_")+">";
        String query = "select ?subject "+
                "(SAMPLE(?numChampionships) AS ?numChampionships) \n" +
                "(SAMPLE(?fastestLap) AS ?fastestLap)\n" +
                "(SAMPLE(?firstRace) AS ?firstRace)\n" +
                "(SAMPLE(?firstWin) AS ?firstWin)\n" +
                "(SAMPLE(?lastRace) AS ?lastRace)\n" +
                "(SAMPLE(?lastWin) AS ?lastWin)\n" +
                "(SAMPLE(?numPodiums) AS ?numPodiums)\n" +
                "(SAMPLE(?numRaces) AS ?numRaces)\n" +
                "(SAMPLE(?numPoles) AS ?numPoles)\n" +
                "where {\n" +
                " Values ?subject {"+subject+"} . "+
                "?subject rdf:type dbo:FormulaOneRacer.\n" +
                "         OPTIONAL {?subject dbo:championships ?numChampionships.}\n" +
                "         OPTIONAL {?subject dbo:fastestLap ?fastestLap.}\n" +
                "         OPTIONAL {?subject dbo:firstRace ?firstRace.}\n" +
                "         OPTIONAL {?subject dbo:firstWin ?firstWin.}\n" +
                "         OPTIONAL {?subject dbo:lastRace ?lastRace.}\n" +
                "         OPTIONAL {?subject dbo:lastWin ?lastWin.}\n" +
                "         OPTIONAL {?subject dbo:podiums ?numPodiums.}\n" +
                "         OPTIONAL {?subject dbo:races ?numRaces.}\n" +
                "         OPTIONAL {?subject dbo:poles ?numPoles.}\n" +
                "}";
        return QueryUtil.getResultFromQuery(query);
    }
}
