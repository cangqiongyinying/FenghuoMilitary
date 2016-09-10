package phone1000.com.firemilitary.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import phone1000.com.firemilitary.R;

public class TuiJianReplaceFragment extends BaseFragment {


    public TuiJianReplaceFragment() {
        // Required empty public constructor
    }

    public static TuiJianReplaceFragment newInstance() {
        TuiJianReplaceFragment fragment = new TuiJianReplaceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tui_jian_replace, container, false);
        View viewById = view.findViewById(R.id.button1);
//        viewById.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity activity = (MainActivity) getActivity();
//                FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.mainactivity_framelayout,activity.tuijian);
//                fragmentTransaction.hide(TuiJianReplaceFragment.newInstance());
//                fragmentTransaction.commit();
//            }
//        });
        return view;
    }

}
