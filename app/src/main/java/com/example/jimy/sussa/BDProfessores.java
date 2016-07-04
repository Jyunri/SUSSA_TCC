package com.example.jimy.sussa;

import java.util.HashMap;

/**
 * Created by jimy on 28/06/16.
 */
public class BDProfessores {
    static HashMap<String, Professor> hashProfessor = createHashProfessores();

    static HashMap createHashProfessores() {
        HashMap<String,Professor> map = new HashMap<>();
        map.put("Cerebro", new Professor("Cerebro",R.drawable.cerebro));
        map.put("Beakman", new Professor("Beakman",R.drawable.beakman));
        map.put("Frink", new Professor("Frink",R.drawable.frink));
        map.put("Brown",new Professor("Brown",R.drawable.brown));

        return map;
    }
}
