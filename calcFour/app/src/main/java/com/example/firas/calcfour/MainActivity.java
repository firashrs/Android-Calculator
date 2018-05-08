package com.example.firas.calcfour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //private String equation = "";
    private TextView afficheur = null;

    private void addToEquation(String added){
        if(afficheur.getText().toString().equals("0")) {
            if(added.equals("+")) return;
            if(added.equals("–")) return;
            if(added.equals("^")) return;
            if(added.equals("x")) return;
            if(added.equals("/")) return;
            if(added.equals("%")) return;
            afficheur.setText(added);
        }else {
            afficheur.setText(afficheur.getText() + added);
        }

       /* if( added.equals("x") ) added="*";
        equation += added;*/
    }

    private void clear(){
        //equation="";
        afficheur.setText("0");
    }

    private void delete_last(){
       // equation=equation.substring(0, equation.length()-1);
        if( (afficheur.getText().length() == 0) || (afficheur.getText().toString().equals("0")) ) return;
        afficheur.setText( afficheur.getText().subSequence(0, afficheur.getText().length()-1));
        if( afficheur.getText().length() == 0 ) afficheur.setText("0");

    }
    private void square_rooting(){
        calculate_equal();
        if((afficheur.getText().length() == 0) || (afficheur.getText().toString().equals("0")) ) return;
        double a = Double.parseDouble(afficheur.getText().toString());
        if(a<=0) return;

        a= Math.sqrt(a);

        String result_string;
        int test_res_int = (int) a;
        if(test_res_int == a){
            result_string = test_res_int+"";
        }else{
            result_string = a+"";
        }

        afficheur.setText(result_string);
    }
    private void signing(){
        calculate_equal();
        String aff = afficheur.getText().toString();
        if( (aff.length() == 0) || (aff.toString().equals("0")) ) return;

        if (aff.charAt(0) == '-' )  {
            afficheur.setText(aff.substring(1,aff.length()));
        }else {
            afficheur.setText("-"+aff);
        }
    }
    private void factorieling(){

        calculate_equal();
        if( afficheur.getText().length() == 0)return;
        float a = Float.parseFloat(afficheur.getText().toString());
        int aa = (int) a;

        if(aa<1) return;
        int res = 1;
        for(int i=1;i<=aa;i++){
            res*=i;
        }
        afficheur.setText(res + "");
    }
    private void squaring(){
        calculate_equal();
        if(afficheur.getText().length() == 0) return;
        float a = Float.parseFloat(afficheur.getText().toString());
        if(a<=0) return;

        a = a*a;

        String result_string;
        int test_res_int = (int) a;
        if(test_res_int == a){
            result_string = test_res_int+"";
        }else{
            result_string = a+"";
        }

        afficheur.setText(result_string);
    }
    private String fn(String s){
        Log.w("fn",s);
        int start = -1;
        int end = -1;
        int toSkip = 0;
        for(int i=0; i<s.length(); i++){
            if( s.charAt(i) == '(' ){
                if(start == -1){
                    start = i;
                }else{
                    toSkip++;
                }
            }else if( s.charAt(i) == ')' ){
                if(toSkip==0){
                    end = i;
                    break;
                }else{
                    toSkip--;
                }
            }
        }

        if(start != -1) {
            return fn(s.substring(0, start) + fn(s.substring(start + 1, end)) + s.substring(end + 1, s.length()) );
        /*Log.w("start",start+"");
        Log.w("end",end+""  );
        Log.w("part 1",s.substring(0, start) +"" );
        Log.w("part two", s.substring(start, end+1)+"" );
        Log.w("part 3", s.substring(end+1, s.length())+"");
*/
        }else{
            int findOp = s.indexOf('%');
            if(findOp == -1) findOp = s.indexOf('x');
            if(findOp == -1) findOp = s.indexOf('/');
            if( findOp == -1 ) findOp = s.indexOf('^');
            if( findOp == -1 ) findOp = s.indexOf('+');
            if( findOp == -1 ) findOp = s.indexOf('–');
            if(findOp == -1) return s;

            int left_limit = findOp;
            int right_limit = findOp;

            //find right
            for(int i = findOp+1; i<s.length();i++){

                if( (s.charAt(i)=='0') || (s.charAt(i)=='1') || (s.charAt(i)=='2') || (s.charAt(i)=='3') ||
                        (s.charAt(i)=='4') || (s.charAt(i)=='5') || (s.charAt(i)=='6') || (s.charAt(i)=='7') ||
                        (s.charAt(i)=='8') || (s.charAt(i)=='9') || (s.charAt(i)=='.')  || (s.charAt(i)=='-')) {
                    right_limit++;
                }else{
                    break;
                }
            }

            //find left
            for(int i = findOp-1; i>=0;i--){
                if( (s.charAt(i)=='0') || (s.charAt(i)=='1') || (s.charAt(i)=='2') || (s.charAt(i)=='3') ||
                        (s.charAt(i)=='4') || (s.charAt(i)=='5') || (s.charAt(i)=='6') || (s.charAt(i)=='7') ||
                        (s.charAt(i)=='8') || (s.charAt(i)=='9') || (s.charAt(i)=='.') || (s.charAt(i)=='-') ) {
                    left_limit--;
                }else{
                    break;
                }
            }

            String firstNbr= s.substring(findOp+1, right_limit+1);
            String secondNbr= s.substring(left_limit, findOp);
            float fstNbr = Float.parseFloat(firstNbr);
            float secNbr = Float.parseFloat(secondNbr);
            float res =0;
            if(s.charAt(findOp) == 'x'){
                res = fstNbr*secNbr;
            }else if(s.charAt(findOp) == '/'){
                if(fstNbr!=0)
                    res = secNbr/fstNbr;
                else
                    res = 0;
            }else if(s.charAt(findOp) == '+'){
                res = fstNbr+secNbr;
            }else if(s.charAt(findOp) == '–'){
                res = secNbr-fstNbr;
            }else if(s.charAt(findOp) == '^'){
                res = 1;
                for(int k=0;k<fstNbr;k++){
                    res *= secNbr;
                }
            }else if(s.charAt(findOp) == '%'){
                int fN = (int)fstNbr;
                int sN = (int)secNbr;
                res = sN%fN;
            }
            String result_string;

            int test_res_int = (int) res;
            if(test_res_int == res){
                result_string = test_res_int+"";
            }else{
                result_string = res+"";
            }

            Log.w("become",s.substring(0,left_limit)+result_string+s.substring(right_limit+1,s.length()));

            return fn(s.substring(0,left_limit)+result_string+s.substring(right_limit+1,s.length()) );
        }

    }

    private void calculate_equal(){

        String calculated = fn(afficheur.getText().toString());
        Log.w("finalShow", calculated);
        afficheur.setText(calculated);
        //int value = Integer.parseInt(equation);
       // afficheur.setText(value+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inti afficheur
        afficheur = (TextView) findViewById(R.id.textView);
        afficheur.setText("0");


        //////////// CONTROLS ///////////////////////////
        //handle button clr
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               clear();
                                           }
                                       }
        );

        //handle button delete
        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               delete_last();
                                           }
                                       }
        );
        //handle button sqr
        Button buttonSquare = (Button) findViewById(R.id.buttonSquare);
        buttonSquare.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               squaring();
                                           }
                                       }
        );
        //handle button sqrt
        Button buttonSquareRoot = (Button) findViewById(R.id.buttonSquareRoot);
        buttonSquareRoot.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               square_rooting();
                                           }
                                       }
        );
        //handle button +/-
        Button buttonSign = (Button) findViewById(R.id.buttonSign);
        buttonSign.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               signing();
                                           }
                                       }
        );
        //handle button equals
        Button buttonEqual = (Button) findViewById(R.id.buttonEqual);
        buttonEqual.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               calculate_equal();
                                           }
                                       }
        );
        //handle button fact
        Button buttonFact = (Button) findViewById(R.id.buttonFact);
        buttonFact.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               factorieling();
                                           }
                                       }
        );


        ////////// WRITING ////////////////////////////

        //handle button 3
        Button buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonThree.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("3");
                                           }
                                       }
        );

        //handle button 2
        Button buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonTwo.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("2");
                                           }
                                       }
        );


        //handle button 1
        Button buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonOne.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("1");
                                           }
                                       }
        );

        //handle button times
        Button buttonTimes = (Button) findViewById(R.id.buttonTimes);
        buttonTimes.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("x");
                                           }
                                       }
        );

        //handle button divide
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonDivide.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("/");
                                           }
                                       }
        );

        //handle button power
        Button buttonPower = (Button) findViewById(R.id.buttonPower);
        buttonPower.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("^");
                                           }
                                       }
        );

        //handle button point
        Button buttonPoit = (Button) findViewById(R.id.buttonPoit);
        buttonPoit.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation(".");
                                           }
                                       }
        );

        //handle button 1
        Button buttonZero = (Button) findViewById(R.id.buttonZero);
        buttonZero.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("0");
                                           }
                                       }
        );

        //handle button mod
        Button buttonMod = (Button) findViewById(R.id.buttonMod);
        buttonMod.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v){
                                             addToEquation("%");
                                         }
                                     }
        );

        //handle button 6
        Button buttonSix = (Button) findViewById(R.id.buttonSix);
        buttonSix.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v){
                                             addToEquation("6");
                                         }
                                     }
        );
        //handle button 5
        Button buttonFive = (Button) findViewById(R.id.buttonFive);
        buttonFive.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v){
                                             addToEquation("5");
                                         }
                                     }
        );
        //handle button 4
        Button buttonFour = (Button) findViewById(R.id.buttonFour);
        buttonFour.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v){
                                              addToEquation("4");
                                          }
                                      }
        );
        //handle button +
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v){
                                              addToEquation("+");
                                          }
                                      }
        );
        //handle button -
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v){
                                              addToEquation("–");
                                          }
                                      }
        );
        //handle button 9
        Button buttonNine = (Button) findViewById(R.id.buttonNine);
        buttonNine.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v){
                                              addToEquation("9");
                                          }
                                      }
        );
        //handle button 8
        Button buttonEight = (Button) findViewById(R.id.buttonEight);
        buttonEight.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v){
                                              addToEquation("8");
                                          }
                                      }
        );
        //handle button 7
        Button buttonSeven = (Button) findViewById(R.id.buttonSeven);
        buttonSeven.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("7");
                                           }
                                       }
        );
        //handle button )
        Button buttonRightBrackey = (Button) findViewById(R.id.buttonRightBrackey);
        buttonRightBrackey.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation(")");
                                           }
                                       }
        );
        //handle button (
        Button buttonLeftBrackey = (Button) findViewById(R.id.buttonLeftBrackey);
        buttonLeftBrackey.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               addToEquation("(");
                                           }
                                       }
        );


    }
}
