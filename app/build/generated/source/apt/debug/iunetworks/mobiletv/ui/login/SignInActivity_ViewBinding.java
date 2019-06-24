// Generated code from Butter Knife. Do not modify!
package iunetworks.mobiletv.ui.login;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import iunetworks.mobiletv.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignInActivity_ViewBinding implements Unbinder {
  private SignInActivity target;

  private View view7f080024;

  @UiThread
  public SignInActivity_ViewBinding(SignInActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignInActivity_ViewBinding(final SignInActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.button_login, "field 'buttonLogin' and method 'performLogin'");
    target.buttonLogin = Utils.castView(view, R.id.button_login, "field 'buttonLogin'", Button.class);
    view7f080024 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.performLogin(p0);
      }
    });
    target.emailEditText = Utils.findRequiredViewAsType(source, R.id.edit_text_email, "field 'emailEditText'", EditText.class);
    target.emailErrorTextView = Utils.findRequiredViewAsType(source, R.id.text_view_email_error, "field 'emailErrorTextView'", TextView.class);
    target.passwordErrorTextView = Utils.findRequiredViewAsType(source, R.id.text_view_password_error, "field 'passwordErrorTextView'", TextView.class);
    target.passwordEditText = Utils.findRequiredViewAsType(source, R.id.edit_text_password, "field 'passwordEditText'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignInActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.buttonLogin = null;
    target.emailEditText = null;
    target.emailErrorTextView = null;
    target.passwordErrorTextView = null;
    target.passwordEditText = null;

    view7f080024.setOnClickListener(null);
    view7f080024 = null;
  }
}
