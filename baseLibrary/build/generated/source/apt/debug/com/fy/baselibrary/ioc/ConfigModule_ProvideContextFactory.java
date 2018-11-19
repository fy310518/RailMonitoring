// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.fy.baselibrary.ioc;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ConfigModule_ProvideContextFactory implements Factory<Context> {
  private final ConfigModule module;

  public ConfigModule_ProvideContextFactory(ConfigModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Context get() {
    return Preconditions.checkNotNull(
        module.provideContext(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Context> create(ConfigModule module) {
    return new ConfigModule_ProvideContextFactory(module);
  }
}
