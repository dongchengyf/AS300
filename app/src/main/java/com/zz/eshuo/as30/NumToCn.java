package com.zz.eshuo.as30;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NumToCn extends AppCompatActivity {


    //这里使用String， StringBuffer其实都可以；但从可变长度和单线程方面考虑，StringBuilder较为合适；
    private static StringBuilder sb = new StringBuilder();

    private static final String[] CN_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] CN_UNIT = {"分", "角", "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟", "顺"};

    //一些额外附加的字符
    private static final String CN_NEGATIVE = "负";
    private static final String CN_FULL = "整";
    private static final String CN_ZERO_FULL = "零圆整";
    private static final int PERCISION = 2; // 精度
    @InjectView(R.id.et_salary)
    EditText etSalary;
    @InjectView(R.id.bt_in)
    Button btIn;
    @InjectView(R.id.tv_info)
    TextView tvInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numtocn);
        ButterKnife.inject(this);


    }

    public static String numToCn(BigDecimal numOfMoney) {

        //当此 BigDecimal 的值为负、零或正时，返回 -1、0 或 1。
        int signum = numOfMoney.signum();

        //若输入为0，输出零圆整；
        if (signum == 0) {
            return CN_ZERO_FULL;
        }

        //对金额进行四舍五入转化为long类型的整数；先将数的小数点向右移两位，然后在四舍五入，取绝对值，最后将它转换为长整型；
        long number = numOfMoney.movePointRight(PERCISION).setScale(0, BigDecimal.ROUND_HALF_UP).abs().longValue();
        int numIndex = 0; //记录数字的个数；
        boolean getZero = false;
/*
 * 思路：要先判断一下小数部分的具体情况；究其根本是因为：小数部分和整数部分在处理“0”的问题上略有不同；避免出现如图1所示的情况；
 */
        //得到小数部分（小数点后两位）；
        long scale = number % 100;
        if (scale == 0) { //若小数部分为"00"时的情况；骚年，不要忘了在最后追加特殊字符：整
            numIndex += 2;
            getZero = true;
            number /= 100;  // 从number去掉为0数；
            sb.append(CN_FULL);
        } else if (scale % 10 == 0) { //若小数部分为"*0"时的情况；
            numIndex += 1;
            getZero = true;
            number /= 10;// 从number去掉为0数；
        }

        //排除上述两种小数部分的特殊情况，则对小数和整数的处理就是一样一样一样地了！
        while (true) {
            //循环结束条件；
            if (number <= 0) {
                break;
            }

            //每次通过取余来得到最后一位数；
            int numUnit = (int) (number % 10);
            if (numUnit != 0) {
                sb.insert(0, CN_UNIT[numIndex]);  //先添加单位
                sb.insert(0, CN_NUMBER[numUnit]); //在添加根据数字值来对应数组中的中文表述；
                getZero = false; //表明当前数不是0；
            } else {
                //意思是它的上一次的数不是零，那么打印出零；
                if (!getZero) {
                    sb.insert(0, CN_NUMBER[numUnit]);
                }
                //若角分位为零，那么打印零；
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UNIT[numIndex]);
                    }
                } else if ((numIndex - 2) % 4 == 0 && number % 1000 != 0) { //第一个条件是为了每隔4位，打印“圆，万，亿”；第二个条件是为了避免出现如图3的情况；
                    sb.insert(0, CN_UNIT[numIndex]);
                }
                getZero = true; //将其置为true,那么如果下一位还是0,也不会再打印一遍'零'；避免出现图2的情况；
            }

            // 从number每次都去掉最后一个数
            number = number / 10;
            numIndex++;
        }

        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }

        return sb.toString();
    }


    @OnClick(R.id.bt_in)
    public void onViewClicked() {

        BigDecimal numOfMoney = new BigDecimal(etSalary.getText().toString());
        String s = NumToCn.numToCn(numOfMoney);
//        System.out.println(s);
        tvInfo.setText(s);
    }
}