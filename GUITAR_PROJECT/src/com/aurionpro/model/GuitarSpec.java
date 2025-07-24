package com.aurionpro.model;

public class GuitarSpec 
{
    private Builder builder;
    private String model;
    private Type type;
    private Wood backWood;
    private Wood topWood;

    public GuitarSpec(Builder builder, String model, Type type, Wood backWood, Wood topWood) 
    {
        this.builder = builder;
        this.model = (model != null) ? model.toLowerCase() : "";
        this.type = type;
        this.backWood = backWood;
        this.topWood = topWood;
    }

    public Builder getBuilder() 
    {
        return builder;
    }

    public String getModel() 
    {
        return model;
    }

    public Type getType() 
    {
        return type;
    }

    public Wood getBackWood() 
    {
        return backWood;
    }

    public Wood getTopWood()
    {
        return topWood;
    }

    public boolean matches(GuitarSpec otherSpec) 
    {
        if (builder != null && builder != otherSpec.getBuilder())
            return false;

        if (model != null && !model.isEmpty() &&
            !model.equalsIgnoreCase(otherSpec.getModel()))
            return false;

        if (type != null && type != otherSpec.getType())
            return false;

        if (backWood != null && backWood != otherSpec.getBackWood())
            return false;

        if (topWood != null && topWood != otherSpec.getTopWood())
            return false;

        return true;
    }


}
