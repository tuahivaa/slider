package edu.byuh.cis.cs203.slide203ai.util;

public interface TickListener {

    //All methods in an interface are inherently public and abstract, so no need to write:
    //public abstract void onTick();
    //You can just write:
    void onTick();
}
