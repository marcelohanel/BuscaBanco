package br.com.mmmobile.buscabanco.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.com.mmmobile.buscabanco.Activities.Detalhes;
import br.com.mmmobile.buscabanco.Comparators.comTableBancos;
import br.com.mmmobile.buscabanco.DataBase.TableBancos;
import br.com.mmmobile.buscabanco.DataBase.TableConfig;
import br.com.mmmobile.buscabanco.Diversos.Funcoes;
import br.com.mmmobile.buscabanco.Interfaces.InterfaceBuscaBancos;
import br.com.mmmobile.buscabanco.R;
import br.com.mmmobile.buscabanco.Tasks.TaskBuscaBancos;

public class AdapterBancos extends RecyclerView.Adapter<AdapterBancos.MyViewHolder> implements InterfaceBuscaBancos {

    public List<TableBancos> mListBancos;
    public int mItemSelected;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private RecyclerView mRecyclerView;
    public int mOrdem;

    public AdapterBancos(Context c, RecyclerView r) {
        mContext = c;
        mOrdem = 0;
        mItemSelected = -1;
        mRecyclerView = r;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListBancos = getDados();
    }

    private List<TableBancos> getDados() {

        List<TableBancos> mList;

        TableBancos record = new TableBancos();
        mList = record.getListProximidade("0.0", "0.0", "0");

        return mList;
    }

    public void order() {
        comTableBancos mComparator = new comTableBancos(mOrdem);
        Collections.sort(mListBancos, mComparator);
        notifyDataSetChanged();
        setSelected(-1);
    }

    public void refresh() {
        TaskBuscaBancos taskBuscaBancos = new TaskBuscaBancos(mContext, this);
        taskBuscaBancos.execute();
    }

    private void setSelected(int position) {

        notifyItemChanged(mItemSelected);
        this.mItemSelected = position;
        notifyItemChanged(mItemSelected);

        mRecyclerView.scrollToPosition(mItemSelected);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = mLayoutInflater.inflate(R.layout.list_bancos, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        myViewHolder.txtID.setText(String.valueOf(mListBancos.get(i).getId()));
        myViewHolder.txtBanco.setText(mListBancos.get(i).getNomeBanco());
        myViewHolder.txtAgencia.setText(mListBancos.get(i).getNomeAgencia());

        String sDetalhe = "";
        sDetalhe = sDetalhe.concat(mListBancos.get(i).getCidade());

        if (mListBancos.get(i).getBairro().trim().length() != 0) {
            sDetalhe = sDetalhe.concat(" - ");
            sDetalhe = sDetalhe.concat(mListBancos.get(i).getBairro());
        }

        if (mListBancos.get(i).getFone().trim().length() != 0) {
            sDetalhe = sDetalhe.concat(System.getProperty("line.separator"));
            sDetalhe = sDetalhe.concat(mListBancos.get(i).getFone());
        }

        if (mListBancos.get(i).getTipo().trim().length() != 0) {
            sDetalhe = sDetalhe.concat(System.getProperty("line.separator"));
            sDetalhe = sDetalhe.concat(mListBancos.get(i).getTipo());
        }

        myViewHolder.txtDetalhes.setText(sDetalhe);
        myViewHolder.txtDistancia.setText(Funcoes.decimalFormat.format(mListBancos.get(i).getDistancia()));

        if (mItemSelected == i)
            myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primary_light));
        else
            myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.icons));
    }

    @Override
    public int getItemCount() {
        return mListBancos.size();
    }

    @Override
    public void onFinishTask(List<TableBancos> s) {

        mItemSelected = -1;
        mListBancos = s;

        notifyDataSetChanged();
        order();

        mRecyclerView.scrollToPosition(0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtID;
        public TextView txtBanco;
        public TextView txtAgencia;
        public TextView txtDetalhes;
        public TextView txtDistancia;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtID = (TextView) itemView.findViewById(R.id.txtID);
            txtBanco = (TextView) itemView.findViewById(R.id.txtBanco);
            txtAgencia = (TextView) itemView.findViewById(R.id.txtAgencia);
            txtDetalhes = (TextView) itemView.findViewById(R.id.txtDetalhes);
            txtDistancia = (TextView) itemView.findViewById(R.id.txtDistancia);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(getAdapterPosition());
                    Intent intent = new Intent(mContext, Detalhes.class);
                    intent.putExtra("id", Integer.valueOf(txtID.getText().toString()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
