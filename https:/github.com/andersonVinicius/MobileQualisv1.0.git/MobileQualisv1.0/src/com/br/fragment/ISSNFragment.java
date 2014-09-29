package com.br.fragment;





import com.br.activity.R;
import com.br.dao.LinguagemDataSource;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

public class ISSNFragment extends Fragment{
	private static EditText text;
	
	public ISSNFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.fragment_issn, container, false);
    	
        text = (EditText)rootView.findViewById(R.id.editText1);
        Button but = (Button) rootView.findViewById(R.id.button1);
        but.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//resolver valor nulos 
				
			if(!text.getText().toString().equals("")){

				String issn = text.getText().toString();
				LinguagemDataSource source= new LinguagemDataSource(getActivity());
				source.inserirHistorico(issn, "issn");
				ListaTodosFragment fragment = new ListaTodosFragment(issn,2);
				
			
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).addToBackStack("tag1").commit();
			
			}else{
				

			//	Log.i("CATEGORIA 31:", issn);
				AlertDialog.Builder alerta = new AlertDialog.Builder(rootView.getContext());
				alerta.setIcon(R.drawable.ic_title4);
				alerta.setTitle("ISSN Invalido");
				alerta.setMessage("Digite um ISSN valido ! ");
				alerta.show();
				

				
			}
			}
		}); 
        		
        return rootView;
    }
	
	@Override
	public void onDestroy(){
		
		super.onDestroy();
		
		
	}
	

	
}
