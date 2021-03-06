package fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoespricecomparision.R;
import com.example.shoespricecomparision.admin.ListShoesActivity;

import java.util.List;

import adapter.ShoesAdapter;
import adapter.ShoesAdapterAdmin;
import model.Shoes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerViewListShoes;
    private EditText etSearchShoes;
    private Button btnSearchShoes;
    ImageView imgChange;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the shoes_admin for this fragment
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        recyclerViewListShoes = (RecyclerView) view.findViewById(R.id.recyclerViewListShoes);

        etSearchShoes = view.findViewById(R.id.etSearchShoes);
        btnSearchShoes = view.findViewById(R.id.btnSearchShoes);

        btnSearchShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        imgChange = view.findViewById(R.id.change);
        imgChange.setOnClickListener(this);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);

        Call<List<Shoes>> listCall = shoesAPI.getShoes();
        listCall.enqueue(new Callback<List<Shoes>>() {
            @Override
            public void onResponse(Call<List<Shoes>> call, Response<List<Shoes>> response) {
                if (!response.isSuccessful()){
//                    use snackbar here
                    Toast.makeText(getActivity(), "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }
                List<Shoes> items =  response.body();
                ShoesAdapter shoesAdapter = new ShoesAdapter(items,getActivity());
                recyclerViewListShoes.setAdapter(shoesAdapter);
                recyclerViewListShoes.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Shoes>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error", t.getLocalizedMessage());
            }
        });


        return view;

    }

    private void search() {
//        validate if shoes name is empty or not
        String shoesName = etSearchShoes.getText().toString();

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);

        Call<List<Shoes>> listCall = shoesAPI.getShoeById(Integer.parseInt(shoesName));
        listCall.enqueue(new Callback<List<Shoes>>() {
            @Override
            public void onResponse(Call<List<Shoes>> call, Response<List<Shoes>> response) {
                if (!response.isSuccessful()){
//                    use snackbar here
                    Toast.makeText(getActivity(), "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }
                List<Shoes> items =  response.body();
                ShoesAdapter shoesAdapter = new ShoesAdapter(items,getActivity());
                recyclerViewListShoes.setAdapter(shoesAdapter);
                recyclerViewListShoes.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Shoes>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure:  "  + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.change){

        recyclerViewListShoes.setLayoutManager(new GridLayoutManager(getActivity(),2));
        }

    }
}


