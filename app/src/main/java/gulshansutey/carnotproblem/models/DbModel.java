package gulshansutey.carnotproblem.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by asus on 11/4/2017.
 */

@Table(name="database")
public class DbModel extends Model{

    @Column (name="Tag")
    public String tag;


    @Column(name="StartTimeStamp")
    public long startTimeStamp;

    @Column(name="SaveStartTimeStamp")
    public long saveStartTimeStamp;



    @Column(name="EndTimeStamp")
    public long endTimeStamp;

    @Column(name="SaveEndTimeStamp")
    public long saveEndTimeStamp;


}
