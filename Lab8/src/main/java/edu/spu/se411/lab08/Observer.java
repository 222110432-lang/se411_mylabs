package edu.spu.se411.lab08;

public interface Observer {
    /** Updates the observer with the subject's current data. */
    void update(Subject subject);
}
