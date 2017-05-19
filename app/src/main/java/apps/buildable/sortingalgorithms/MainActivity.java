package apps.buildable.sortingalgorithms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import apps.buildable.sortingalgorithms.sort.enums.SortingAlgorithmType;
import apps.buildable.sortingalgorithms.sort.ui.SortingActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.quickSortBtn)
    Button quickSortBtn;
    @BindView(R.id.insertionSortBtn)
    Button insertionSortBtn;
    @BindView(R.id.MergeSortBtn)
    Button mMergeSortBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.quickSortBtn, R.id.insertionSortBtn,R.id.MergeSortBtn})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, SortingActivity.class);
        intent.putExtra(SortingAlgorithmType.class.getName(), (String) view.getTag());
        startActivity(intent);
    }
}
