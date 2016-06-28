package com.example.jimy.sussa;

import java.util.HashMap;

/**
 * Created by jimy on 28/06/16.
 */
public class BDProfessores {
    HashMap<String, Professor> bdProfessor;
    Professor cerebro = new Professor("Cerebro",R.drawable.cerebro);
    Professor beakman = new Professor("Beakman",R.drawable.beakman);
    Professor frink = new Professor("Frink",R.drawable.frink);
    Professor brown = new Professor("Brown",R.drawable.brown);

    public BDProfessores() {
        bdProfessor = new HashMap<>();
        bdProfessor.put("Cerebro",cerebro);
        bdProfessor.put("Beakman",beakman);
        bdProfessor.put("Frink",frink);
        bdProfessor.put("Brown",brown);
    }
}
