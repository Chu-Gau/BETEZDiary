package com.betezteam.betezdiary;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class CGButton {

    private Activity activity;

    private RelativeLayout rootLayout;
    private RelativeLayout layer2;
    private Button rootButton, level2North, level2West, level2South;


    public CGButton(Context context) {
        this.activity = (Activity) context;

        this.rootLayout = activity.findViewById(R.id.cg_button);
        this.layer2 = activity.findViewById(R.id.cg_button_level_2);

        this.rootButton = activity.findViewById(R.id.cg_button_root);
        this.level2North = activity.findViewById(R.id.cg_button_level_2_north);
        this.level2West = activity.findViewById(R.id.cg_button_level_2_west);
        this.level2South = activity.findViewById(R.id.cg_button_level_2_south);

        layer2.setVisibility(View.GONE);

        this.assignEvent();

//        this.setPosition();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void assignEvent() {
        rootButton.setOnTouchListener(new View.OnTouchListener() {
            private Button currentButton;
            private CGButton cgButton;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                cgButton = CGButton.this;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        cgButton.layer2.setVisibility(View.VISIBLE);
                        currentButton = rootButton;
                        highlightCurrent();
                        break;

                    case MotionEvent.ACTION_UP:
                        cgButton.layer2.setVisibility(View.GONE);
                        setAction();
                        unHighlight();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (isPointWithin(event, level2North)) {
                            currentButton = level2North;
                        } else if (isPointWithin(event, level2West)) {
                            currentButton = level2West;
                        } else if (isPointWithin(event, level2South)) {
                            currentButton = level2South;
                        } else if (isPointWithin(event, rootButton)) {
                            currentButton = rootButton;
                        }
                        highlightCurrent();
                        break;
                    default:
                        break;

                }
                return false;
            }

            private void setAction() {
                EditText mainContent = activity.findViewById(R.id.main_content);
                switch (currentButton.getId()) {
                    case R.id.cg_button_root:
                        mainContent.append(". ");
                        break;
                    case R.id.cg_button_level_2_north:
                        mainContent.append(", ");
                        break;
                    case R.id.cg_button_level_2_west:
                        mainContent.append("! ");
                        break;
                    case R.id.cg_button_level_2_south:
                        mainContent.append("? ");
                        break;
                }
            }

            private void unHighlight() {
                rootButton.getBackground().setAlpha(255);
                level2North.getBackground().setAlpha(255);
                level2West.getBackground().setAlpha(255);
                level2South.getBackground().setAlpha(255);
            }

            private void highlightCurrent() {
                unHighlight();
                currentButton.getBackground().setAlpha(128);
            }

            private boolean isPointWithin(MotionEvent event, View view) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int x1 = view.getLeft();
                int x2 = view.getRight();
                int y1 = view.getTop();
                int y2 = view.getBottom();
                return (x <= x2 && x >= x1 && y <= y2 && y >= y1);
            }
        });

    }


}
