package com.example.nugget; 

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
//이번에는 디자인, 추가 페이지, 색감 조정, 4대보험 적용 시, 다크모드, 스플레쉬툰 그림 확인하기
    private EditText editCompany, editHourlyRate, editHoursWorked, editDaysWorked;
    private Switch switchTax;
    private Button btnCalculate, btnTip;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCompany = findViewById(R.id.edit_company);
        editHourlyRate = findViewById(R.id.edit_hourly_rate);
        editHoursWorked = findViewById(R.id.edit_hours_worked);
        editDaysWorked = findViewById(R.id.edit_days_worked);
        switchTax = findViewById(R.id.switch_tax);
        btnCalculate = findViewById(R.id.button_calculate);
        textResult = findViewById(R.id.text_salary);
        btnTip = findViewById(R.id.btn_tip);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSalary();
            }
        });

        btnTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTipDialog();
            }
        });
    }

    private void calculateSalary() {
        String company = editCompany.getText().toString();
        String hourlyRateStr = editHourlyRate.getText().toString();
        String hoursWorkedStr = editHoursWorked.getText().toString();
        String daysWorkedStr = editDaysWorked.getText().toString();
        boolean isTaxEnabled = switchTax.isChecked();

        if (company.isEmpty() || hourlyRateStr.isEmpty() ||
                hoursWorkedStr.isEmpty() || daysWorkedStr.isEmpty()) {
            textResult.setText("모든 값을 입력해주세요.");
            return;
        }

        double hourlyRate;
        int hoursWorked, daysWorked;

        try {
            hourlyRate = Double.parseDouble(hourlyRateStr);
            hoursWorked = Integer.parseInt(hoursWorkedStr);
            daysWorked = Integer.parseInt(daysWorkedStr);
        } catch (NumberFormatException e) {
            textResult.setText("유효한 숫자 값을 입력해주세요.");
            return;
        } //2019041061_김민수

        double salary = hourlyRate * hoursWorked * daysWorked;// 급여계산

        if (isTaxEnabled) {//세금
            double tax = salary * 0.033;
            salary -= tax;
        }

        // 한 달 급여를 원으로 표시
        String formattedSalary = String.format("한 달 급여: ₩%,.0f", salary);

        textResult.setText(formattedSalary);
    }

    private void showTipDialog() {//설명 창
        new AlertDialog.Builder(this)
                .setTitle("사용 설명")
                .setMessage("급여 계산기 사용 방법:\n\n1. 회사 이름을 작성하세요.\n2. 시급을 입력하세요.\n3. " +
                        "하루 근무 시간을 입력하세요.\n4. 한 달 근무 일수를 입력하세요.\n5. " +
                        "세금 공제 스위치를 사용하여 세금 공제 여부를 선택하세요.\n6. '계산하기' 버튼을 눌러 한 달 급여를 확인하세요.")
                .setPositiveButton("확인", null)
                .show();
    }
}


