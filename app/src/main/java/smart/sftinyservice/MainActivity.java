package smart.sftinyservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import smart.sftinyservice.gson.DataSImulation;
import smart.sftinyservice.gson.SfCloudClient;
import smart.sftinyservice.gson.SpatialRelation;

public class MainActivity extends AppCompatActivity {
    private Button postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postBtn = (Button)findViewById(R.id.post);
        postBtn.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SfCloudClient cloudClient = new SfCloudClient();
                        DataSImulation data = new DataSImulation();
                        try {
                            data.toRelation();
                            List<SpatialRelation> relations = data.getData();
                            cloudClient.reportBoxStatus(relations);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
//                        SpatialRelation relation  = new SpatialRelation();
//                        relation.setPerson1Id("1");
//                        relation.setPerson1Name("张宇翔");
//                        relation.setPerson2Id("2");
//                        relation.setPerson2Name("卢宏春");
//                        relation.setRelationType("estranged");

                    }
                }).start();
            }
        });
    }
}
