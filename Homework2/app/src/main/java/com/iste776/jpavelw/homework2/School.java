package com.iste776.jpavelw.homework2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jpavelw on 10/5/16.
 */

public class School {
    private String abbreviation;
    private String name;
    private int foundationYear;
    private String location;

    private static Map<String, School> schools;

    static {
        schools = new HashMap<>();
        schools.put("RIT", new School("RIT", "Rochester Institute of Technology", 1829, "Henrietta, New York, U.S."));
        schools.put("ETH Zurich", new School("ETH Zurich", "Swiss Federal Institute of Technology in Zurich", 1855, "Zürich, Switzerland"));
        schools.put("UNIMELB", new School("UNIMELB", "University of Melbourne", 1853, "Parkville, Victoria, Australia"));
        schools.put("UVM", new School("UVM", "University of Vermont", 1791, "Burlington, Vermont, U.S."));
        schools.put("Oxford", new School("Oxford", "University of Oxford", 1096, "Oxford, England, UK"));
        schools.put("NUS", new School("NUS", "National University of Singapore", 1905, "Singapore"));
        schools.put("NWC", new School("NWC", "National War College", 1946, "Washington, D.C., USA"));
        schools.put("U of T", new School("U of T", "University of Toronto", 1827, "Toronto, Ontario, Canada"));
        schools.put("UCL", new School("UCL", "University College London", 1826, "London, UK"));
        schools.put("FSU", new School("FSU", "Florida State University", 1851, "Tallahassee, Florida, USA"));
        schools.put("Imperial", new School("Imperial", "Imperial College London", 1907, "London, UK"));
        schools.put("HKU", new School("HKU", "The University of Hong Kong", 1911, "Pokfulam, HK"));
        schools.put("UW", new School("UW", "University of Washington", 1861, "Seattle, Washington, USA"));
        schools.put("EPFL", new School("EPFL", "Swiss Federal Institute of Technology in Lausanne", 1853, "Écublens, Vaud, Switzerland"));
        schools.put("Edin", new School("Edin", "The University Of Edinburgh", 1582, "Edinburgh, Scotland, UK"));
        schools.put("Stanford", new School("Stanford", "Stanford University", 1891, "Stanford, California, USA"));
        schools.put("UTokyo", new School("UTokyo", "The University of Tokyo", 1877, "Tokyo, Japan"));
        schools.put("HKUST", new School("HKUST", "The Hong Kong University of Science and Technology", 1991, "Clear Water Bay, New Territories, HK"));
        schools.put("MIT", new School("MIT", "Massachusetts Institute of Technology", 1861, "Cambridge, Massachusetts, USA"));
    }

    private School(){}

    private School(String abbreviation, String name, int foundationYear, String location){
        this.abbreviation = abbreviation;
        this.name = name;
        this.foundationYear = foundationYear;
        this.location = location;
    }

    public School(String abbreviation){
        School temp = schools.get(abbreviation);
        this.abbreviation = temp.abbreviation;
        this.name = temp.name;
        this.foundationYear = temp.foundationYear;
        this.location = temp.location;
    }

    public static ArrayList<String> getSchoolAbbreviations(){
        ArrayList<String> itemsArrayList = new ArrayList<>();
        for(String key : schools.keySet())
            itemsArrayList.add(key);
        return itemsArrayList;
    }

    public String getAbbreviation() { return this.abbreviation; }
    public String getName() { return this.name; }
    public int getFoundationYear() { return this.foundationYear; }
    public String getLocation() { return this.location; }
}
