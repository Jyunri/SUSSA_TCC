package com.example.jimy.sussa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Jimy on 30/11/15.
 */
public class ProfDestaque_fragment extends Fragment implements View.OnClickListener {

    View view;
    ImageView ivCerebro,ivBeakman,ivBrown,ivFrink;
    Intent i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profdestq,container,false);

        ivCerebro = (ImageView)view.findViewById(R.id.ivCerebro);
        ivBeakman = (ImageView)view.findViewById(R.id.ivBeakman);
        ivBrown = (ImageView)view.findViewById(R.id.ivBrown);
        ivFrink = (ImageView)view.findViewById(R.id.ivFrink);

        ivCerebro.setOnClickListener(this);
        ivBeakman.setOnClickListener(this);
        ivBrown.setOnClickListener(this);
        ivFrink.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case(R.id.ivCerebro):
                i = new Intent(getContext(),Professor_detalhe.class);
                i.putExtra("nome","Cerebro");
                Toast.makeText(getContext(), "Abrindo detalhes cerebro", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case(R.id.ivBeakman):
                i = new Intent(getContext(),Professor_detalhe.class);
                i.putExtra("nome","Beakman");
                Toast.makeText(getContext(),"Abrindo detalhes beakman",Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case(R.id.ivBrown):
                i = new Intent(getContext(),Professor_detalhe.class);
                i.putExtra("nome","Brown");
                Toast.makeText(getContext(),"Abrindo detalhes brown",Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case(R.id.ivFrink):
                i = new Intent(getContext(),Professor_detalhe.class);
                i.putExtra("nome","Frink");
                Toast.makeText(getContext(),"Abrindo detalhes Frink",Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;

        }
    }
}
