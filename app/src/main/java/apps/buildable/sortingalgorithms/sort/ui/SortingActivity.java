package apps.buildable.sortingalgorithms.sort.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;

import java.lang.ref.WeakReference;
import java.util.List;

import apps.buildable.sortingalgorithms.R;
import apps.buildable.sortingalgorithms.RetrofitInterface;
import apps.buildable.sortingalgorithms.sort.algorithm.InsertionSort;
import apps.buildable.sortingalgorithms.sort.algorithm.MergeSort;
import apps.buildable.sortingalgorithms.sort.algorithm.QuickSort;
import apps.buildable.sortingalgorithms.sort.algorithm.SortingAlgorithm;
import apps.buildable.sortingalgorithms.sort.enums.SortingAlgorithmType;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SortingActivity extends AppCompatActivity {

    @BindView(R.id.sortRecyclerView)
    ListView mSortRecyclerView;
    private Unbinder unbinder;

    static List<Name> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);
        unbinder = ButterKnife.bind(this);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });


        final SortingAlgorithmType sortingAlgorithmType = SortingAlgorithmType.getAlgorithm(getIntent().getExtras().getString(SortingAlgorithmType.class.getName()));
        SortingAlgorithm algorithmClass = null;
        assert sortingAlgorithmType != null;
        switch (sortingAlgorithmType) {

            case QuickSort:
                algorithmClass = new QuickSort();
                break;
            case InsertionSort:
                algorithmClass=new InsertionSort();
                break;
            case MergeSort:
                algorithmClass=new MergeSort();
                break;
        }

        final ProgressDialog loading = ProgressDialog.show(this,
                "Generating names", "Please wait...", false, false);  // for  showing the
        // dialog  where context is the current context, next field is title followed by
        //   message to be shown to the user and in the end intermediate field


        final SortingAlgorithm finalAlgorithmClass = algorithmClass;
        final WeakReference<ListView> recyclerViewWeakReference = new WeakReference<>(mSortRecyclerView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (names == null) {
                    NameGenerator generator = new NameGenerator();
                    // generate 5000 male and female names.

                    names = generator.generateNames(100000);
                }
                final String[] array = new String[names.size()];

                for (int i = 0; i < names.size(); i++) {
                    array[i] = names.get(i).toString();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loading.setTitle("Sorting Names");
                                loading.show();
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        sortOnServer();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        sortLocally();
                                        break;
                                }
                            }

                            private void sortOnServer() {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl("http://samerzmd/cols/api/")
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);


                                        Call<List<String>> bodyCall = retrofitInterface.sort(sortingAlgorithmType.getValue(), array);

                                        bodyCall.enqueue(new Callback<List<String>>() {
                                            @Override
                                            public void onResponse(@NonNull Call<List<String>> call, @NonNull retrofit2.Response<List<String>> response) {
                                                fillList(response.body());
                                            }

                                            @Override
                                            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

                                            }
                                        });

                                    }
                                }).start();
                            }

                            private void sortLocally() {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                                try {
                                                    Thread.sleep(2000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                final List<String> strings = finalAlgorithmClass.sort(array);
                                                fillList(strings);
                                            }
                                        });

                                    }
                                }).start();
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(SortingActivity.this);
                        builder.setMessage("Server Over Local?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                        loading.hide();
                    }
                });

            }

            private void fillList(final List<String> strings) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewWeakReference.get().setAdapter(new ArrayAdapter<>(SortingActivity.this, android.R.layout.simple_list_item_1, strings));


                        loading.dismiss();
                    }

                });
            }
        }).start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
