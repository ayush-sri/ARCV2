package com.arc.arcv2.Utils;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.arc.arcv2.Model.SubjectModel;
import com.arc.arcv2.Model.User;
import com.arc.arcv2.R;

import java.util.ArrayList;

public class Helper {
   public static final String MY_PREF_NAME = "pref";
   public static User user = new User();
   public static int curr_item;
   public static Button next;
   public static ArrayList<SubjectModel> subjectModelArrayList;
   public static String acc_type = "Student";
   public  static void changeStatusBar(Window window) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(ContextCompat.getColor(window.getContext(), R.color.status_bar_color));
   }
}
