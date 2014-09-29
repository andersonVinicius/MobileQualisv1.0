package com.br.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.br.activity.R;
import com.br.adapter.HistoricoArrayAdapter;
import com.br.adapter.LinguagemArrayAdapter;
import com.br.dao.LinguagemDataSource;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

public class HistoricoFragment extends ListFragment implements
		OnItemClickListener {
	private static ListView lstLinguagem;
	private List<JSONObject> linguagens;
	private HistoricoArrayAdapter adapter;
	private LinguagemDataSource source;
	private ArrayAdapter<JSONObject> adp;
	public static String prompt;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		source = new LinguagemDataSource(getActivity().getApplicationContext());
		// vai mudar
		linguagens = source.listaHistorico();

		lstLinguagem = getListView();
		lstLinguagem.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

		lstLinguagem.setMultiChoiceModeListener(new ModeCallback());

		adp = new HistoricoArrayAdapter(getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_1, linguagens);

		lstLinguagem = (ListView) getListView().findViewById(android.R.id.list);
		lstLinguagem.setAdapter(adp);
		lstLinguagem.setOnItemClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_favorito, container,
				false);

		return rootView;
	}

	private class ModeCallback implements ListView.MultiChoiceModeListener,
			OnQueryTextListener, ActionBar.TabListener {

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {

			MenuInflater inflater = getActivity().getMenuInflater();
			inflater.inflate(R.menu.list_select_menu2, menu);
			mode.setTitle("Selecione os Items");
			return true;
		}

		public void shareIt(ArrayList<String> list) {
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			ArrayList<String> shareBody = list;
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Qualis CAPES | PPGEE-UFPA");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					"Qualis CAPES | PPGEE-UFPA \n\n Autor : "
							+ getEmail().toString() + "\n\n"
							+ shareBody.toString().trim().replaceAll(",", ""));
			startActivity(Intent.createChooser(sharingIntent,
					"Compartilha via - Qualis CAPES | PPGEE-UFPA"));

		}

		private String getEmail() {
			try {
				AccountManager accountManager = AccountManager
						.get(getActivity());
				Account[] accounts = accountManager
						.getAccountsByType("com.google");
				if (accounts.length > 0) {
					Account account = accounts[0];
					Toast.makeText(getActivity(), account.name,
							Toast.LENGTH_SHORT).show();
					return account.name;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

			SparseBooleanArray checked = getListView()
					.getCheckedItemPositions();

			switch (item.getItemId()) {
			case R.id.share:
				ArrayList<Integer> positions1 = new ArrayList<Integer>();

				for (int i = 0; i < checked.size(); i++) {
					if (checked.valueAt(i)) {
						positions1.add(checked.keyAt(i));
					}
				}

				Collections.sort(positions1, Collections.reverseOrder());
				ArrayList<String> list = new ArrayList<String>();
				for (int position : positions1) {
					try {
						list.add("======================================================"
								+ "\n");

						list.add(adp.getItem(position).getString("parametro")
								.toString()
								+ "\n");
						list.add(adp.getItem(position).getString("tipo")
								.toString()
								+ "\n");

						list.add("======================================================"
								+ "\n");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				shareIt(list);
				break;
			case R.id.lixo:

				ArrayList<Integer> positions = new ArrayList<Integer>();

				for (int i = 0; i < checked.size(); i++) {
					if (checked.valueAt(i)) {
						positions.add(checked.keyAt(i));
					}
				}

				Collections.sort(positions, Collections.reverseOrder());

				for (int position : positions) {
					try {

						source.deleteHistorico(adp.getItem(position).getInt(
								"id"));

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				Toast.makeText(getActivity(), "Excluido com sucesso!",
						Toast.LENGTH_SHORT).show();

				onActivityCreated(getArguments());

				break;

			default:
				Toast.makeText(getActivity(), "Clicked " + item.getTitle(),
						Toast.LENGTH_SHORT).show();
				break;

			}
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
		}

		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			final int checkedCount = getListView().getCheckedItemCount();
			// lstLinguagem.setItemChecked(checkedCount, true);

			switch (checkedCount) {
			case 0:
				mode.setSubtitle(null);
				break;
			case 1:
				mode.setSubtitle("One item selected");
				break;
			default:
				mode.setSubtitle("" + checkedCount + " items selected");
				break;
			}
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		source.fechar();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {

		try {
			final String param = adp.getItem(position).getString("parametro");
			final String tipo = adp.getItem(position).getString("tipo");

			AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());

			alerta.setIcon(R.drawable.ic_title4);
			alerta.setTitle("Atenção!!");
			alerta.setMessage("Deseja Realizar Busca?");
			// Log.i("CATEGORIA :", area);
			Log.i("CATEGORIA :", tipo);
			// Log.i("CATEGORIA :",param);
			// Log.i("CATEGORIA :", estrato);

			alerta.setPositiveButton("SIM",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Fragment fragment = null;
							if (tipo.toString().trim().equals("issn")) {

								fragment = new ListaTodosFragment(param, 2);
								// FragmentManager fragmentManager =
								// getFragmentManager();
								// fragmentManager
								// .beginTransaction()
								// .replace(R.id.frame_container,
								// fragment).addToBackStack("tag")
								// .commit();

							} else {

								try {
									String area = adp.getItem(position)
											.getString("area");
									String estrato = adp.getItem(position)
											.getString("estrato");

									fragment = new ListaTodosFragment(param,
											area, estrato, 4);

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							FragmentManager fragmentManager = getFragmentManager();
							fragmentManager.beginTransaction()
									.replace(R.id.frame_container, fragment)
									.addToBackStack("tag5").commit();
						}

					});

			alerta.setNegativeButton("NÂO",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							// Code that is executed when clicking NO

							dialog.dismiss();
						}

					});

			AlertDialog alert = alerta.create();
			alert.show();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}