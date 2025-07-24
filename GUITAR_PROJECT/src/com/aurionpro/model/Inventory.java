package com.aurionpro.model;

import java.util.*;

public class Inventory 
{
    private List<Guitar> guitars;

    public Inventory() 
    {
        guitars = new LinkedList<>();
    }

    public void addGuitar(String serialNumber, double price,
                          Builder builder, String model, Type type,
                          Wood backWood, Wood topWood) 
    {
        GuitarSpec spec = new GuitarSpec(builder, model, type, backWood, topWood);
        Guitar guitar = new Guitar(serialNumber, price, spec);
        guitars.add(guitar);
    }

    public Guitar getGuitar(String serialNumber)
    {
        for (Guitar guitar : guitars)
        {
            if (guitar.getSerialNumber().equals(serialNumber)) 
            {
                return guitar;
            }
        }
        return null;
    }

    public List<Guitar> search(GuitarSpec searchSpec) 
    {
        List<Guitar> matchingGuitars = new LinkedList<>();
        for (Iterator<Guitar> i = guitars.iterator(); i.hasNext(); ) {
            Guitar guitar = i.next();
            
            GuitarSpec guitarSpec = guitar.getSpec();

            if (searchSpec.getBuilder() != null && searchSpec.getBuilder() != guitarSpec.getBuilder())
                continue;

            String model = searchSpec.getModel();
            if (model != null && !model.isEmpty() &&
                !model.equalsIgnoreCase(guitarSpec.getModel()))
                continue;

            if (searchSpec.getType() != null && searchSpec.getType() != guitarSpec.getType())
                continue;

            if (searchSpec.getBackWood() != null && searchSpec.getBackWood() != guitarSpec.getBackWood())
                continue;

            if (searchSpec.getTopWood() != null && searchSpec.getTopWood() != guitarSpec.getTopWood())
                continue;

            matchingGuitars.add(guitar);
        }
        return matchingGuitars;
    }

    public List<Guitar> getAllGuitars() {
        return new ArrayList<>(guitars);
    }
}
