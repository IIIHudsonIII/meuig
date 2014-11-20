package com.br.meuig.adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/*
 * nao usar esta classe!!!
 * */

public class ListViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<ItemListView> itens;

	private ListViewAdapter(Context context, ArrayList<ItemListView> itens) {
		
		this.itens = itens;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itens.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		//se der problema, pesquisar esta sobrecarga de metodo.
		return itens.get(position).getIconeRid();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ItemListView item = itens.get(position);//pega o item da posicao
		//convertView = inflater.inflate(R.layout.ite, null);
		
		
		
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
