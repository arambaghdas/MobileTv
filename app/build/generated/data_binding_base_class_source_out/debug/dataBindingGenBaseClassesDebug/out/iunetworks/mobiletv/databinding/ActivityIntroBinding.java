package iunetworks.mobiletv.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import iunetworks.mobiletv.presenter.IntroPresenter;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityIntroBinding extends ViewDataBinding {
  @NonNull
  public final Button signInButton;

  @NonNull
  public final Button signUpButton;

  @Bindable
  protected IntroPresenter mPresenter;

  protected ActivityIntroBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button signInButton, Button signUpButton) {
    super(_bindingComponent, _root, _localFieldCount);
    this.signInButton = signInButton;
    this.signUpButton = signUpButton;
  }

  public abstract void setPresenter(@Nullable IntroPresenter presenter);

  @Nullable
  public IntroPresenter getPresenter() {
    return mPresenter;
  }

  @NonNull
  public static ActivityIntroBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_intro, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityIntroBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityIntroBinding>inflateInternal(inflater, iunetworks.mobiletv.R.layout.activity_intro, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityIntroBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_intro, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityIntroBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityIntroBinding>inflateInternal(inflater, iunetworks.mobiletv.R.layout.activity_intro, null, false, component);
  }

  public static ActivityIntroBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityIntroBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityIntroBinding)bind(component, view, iunetworks.mobiletv.R.layout.activity_intro);
  }
}
