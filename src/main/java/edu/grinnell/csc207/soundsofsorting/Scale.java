package edu.grinnell.csc207.soundsofsorting;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * A musical scale.
 */
public class Scale {
    /** The (MIDI) note values of this scale. */
    private int[] notes;
    
    private static Synthesizer synth;
    private static MidiChannel instrument;
    
    private static final int REGULAR_VELOCITY = 60;
    private static final int EMPHASIZED_VELOCITY = 120;
    
    static {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            instrument = synth.getChannels()[0];
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Create a new Scale with the given MIDI note values.
     * @param notes the (MIDI) note values of this scale (in ascending order)
     */
    public Scale(int[] notes) {
        this.notes = notes;
    }
    
    /**
     * @return the number of notes in the scale
     */
    public int size() {
        return notes.length;
    }
    
    /**
     * Plays a note of the scale.
     * @param index the index of the note to play within the scale
     * @param emphasized true if this note should be emphasized
     */
    public void playNote(int index, boolean emphasized) {
        if (index < 0 || index >= notes.length) return;
        instrument.noteOn(notes[index], emphasized ? EMPHASIZED_VELOCITY : REGULAR_VELOCITY);
    }
}