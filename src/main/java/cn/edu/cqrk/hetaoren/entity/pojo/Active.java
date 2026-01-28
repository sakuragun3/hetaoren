package cn.edu.cqrk.hetaoren.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalTime;

@Data
@TableName("tb_active")  // 对应数据库表名
public class Active {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;                   // 主键
    @TableField("activity_name")
    private String activityName;          // 患教活动名称
    @TableField("doctor_name")
    private String doctorName;            // 主持医生
    @TableField("patient_name")
    private String patientName;           // 患者姓名
    @TableField("start_time")
    private LocalTime startTime;
    @TableField("end_time")
    private LocalTime endTime;
    @TableField("participant_count")
    private Integer participantCount;     // 参加人数
}
