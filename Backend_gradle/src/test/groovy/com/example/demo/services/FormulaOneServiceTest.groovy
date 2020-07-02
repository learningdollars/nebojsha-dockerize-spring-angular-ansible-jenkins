package com.example.demo.services

import net.minidev.json.JSONObject
import net.minidev.json.parser.JSONParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class FormulaOneServiceTest extends Specification {

    @Autowired
    FormulaOneService service

    def "getAbstract with en as lang shoud return the abstract with en lang"(){
        given:
            def jsonParser = new JSONParser()

        when:
            def result = service.getAbstract("en")
            def jsonResponse = (JSONObject) jsonParser.parse(result)
        then:
            result != null
            !result.isEmpty()
            ((JSONObject) jsonParser.parse(jsonResponse.get("abstract").toString())).get("xml:lang").toString().equals("en")
    }

    def "getAbstract with fr as lang shoud return the abstract with fr lang"(){
        given:
        def jsonParser = new JSONParser()

        when:
        def result = service.getAbstract("fr")
        def jsonResponse = (JSONObject) jsonParser.parse(result)
        then:
        result != null
        !result.isEmpty()
        ((JSONObject) jsonParser.parse(jsonResponse.get("abstract").toString())).get("xml:lang").toString().equals("fr")
    }

    def "getAbstract with es as lang shoud return the abstract with es lang"(){
        given:
        def jsonParser = new JSONParser()

        when:
        def result = service.getAbstract("es")
        def jsonResponse = (JSONObject) jsonParser.parse(result)
        then:
        result != null
        !result.isEmpty()
        ((JSONObject) jsonParser.parse(jsonResponse.get("abstract").toString())).get("xml:lang").toString().equals("es")
    }
}
