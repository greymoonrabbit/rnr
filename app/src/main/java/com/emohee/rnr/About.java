package com.emohee.rnr;

import android.content.Intent;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.about);

        TextView ppPara = findViewById(R.id.about_pp_para);
        ppPara.setText("Your Privacy is Important to Us\n" +
                "\n" +
                "In this policy, \"we\", \"us\", \"our\" or \"Run & Recall\" means Run & Recall, \"you\", \"your\" or \"yours\" means the persons to whom this policy applies.\n" +
                "\n" +
                "The security of your personal data is important to us.  Run & Recall has in place safeguards to protect the personal data stored with us. This policy describes how we may collect, use, disclose, process and manage your personal data.\n" +
                "\n" +
                "This policy applies to any individual's personal data which is in our possession or under our control.\n" +
                "\n" +
                "What Personal Data We Collect\n" +
                "\n" +
                "We define \"Personal data\" as data that can be used to uniquely identify a natural person. Personal data can be collected from various sources and processed by us. Some examples of data which on its own or jointly, can be used to identify a natural person are:\n" +
                "\n" +
                "A.  Personal particulars (e.g. name, contact details);\n" +
                "B.  Your personal opinions made known to us e.g. through feedback or surveys;\n" +
                "C.  Information relating to your activities, habits, preferences and interests arising from your use of products and services of Run & Recall, our partners or vendors; and/or\n" +
                "other electronic data or information relating to you such as cookies, activity logs and online identifiers through your usage of our products and services or as part of their delivery to you.\n" +
                "\n" +
                "How We Use Your Personal Data\n" +
                "\n" +
                "We may use your personal data for our business purposes, such as:\n" +
                "\n" +
                "A.  Developing and providing products or services (whether made available by us or through us)\n" +
                "B.  Assessing and processing instructions or requests from you;\n" +
                "C.  Communicating with you, including providing you with updates on changes to products and services (whether made available by us or through us)\n" +
                "D.  Responding to queries or feedback;\n" +
                "E.  Addressing or investigating any complaints, claims or disputes;\n" +
                "F.  Verifying your identity for the purposes of providing products or services;\n" +
                "G.  Complying with all applicable laws, regulations, rules, directives, orders, instructions, guidance and requests from any local or foreign authorities;\n" +
                "H.  Monitoring products and services provided by or made available through us; and/or\n" +
                "I.  Administering benefits or entitlements in connection with our relationship with you or arising from your participation in events, campaigns, or marketing promotions by us or in conjunction with our partners. This will include the administration of loyalty, rewards programmes, lucky draws, and/or sending gifts and awards.\n" +
                "\n" +
                "Disclosure and Sharing of Personal Data\n" +
                "\n" +
                "We may from time to time and in compliance with all applicable laws on data privacy, disclose your personal data to any members of Run & Recall or to third parties, whether located in Singapore or elsewhere, in order to carry out the purposes set out above. Please be assured that when we disclose your personal data to such parties, we require them to ensure that any personal data disclosed to them are kept confidential and secure.\n" +
                "\n" +
                "We wish to emphasise that Run & Recall does not sell personal data to any third parties and we shall remain fully compliant of any duty or obligation of confidentiality imposed on us or any applicable law.\n" +
                "\n" +
                "We may transfer, store, process and/or deal with your personal data outside Singapore. In doing so, we will comply with the PDPA and other applicable data protection and privacy laws, such as the GDPR.\n" +
                "\n" +
                "Other Websites\n" +
                "\n" +
                "Our app may contain links to other websites which are not maintained by Run & Recall. This privacy policy only applies to our app. When visiting these third party websites, you should read their privacy policies which will apply to your use of the websites.\n" +
                "\n" +
                "Retention of Personal Data\n" +
                "\n" +
                "Your personal data is retained as long as the purpose for which it was collected remains and until it is no longer necessary for any other legal or business purposes.\n" +
                "\n" +
                "Access and Correction\n" +
                "\n" +
                "You may request access or make corrections to your personal data held by Run & Recall. Run & Recall may charge a fee for processing your request for access. Such a fee depends on the nature and complexity of your access request. Information on the processing fee will be made available to you.\n" +
                "\n" +
                "Amendments and Updates of Privacy Policy\n" +
                "\n" +
                "We may amend this policy from time to time to ensure that this policy is consistent with any developments to the way Run & Recall uses your personal data or any changes to the laws and regulations applicable to us. All communications, transactions and dealings with us shall be subject to the latest version of this policy in force at the time.\n" +
                "\n" +
                "Last updated: 21 May 2023\n");

    }
}