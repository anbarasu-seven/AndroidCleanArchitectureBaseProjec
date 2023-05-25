// Generated by Dagger (https://dagger.dev).
package com.example.mvvmhilt.repos;

import com.example.mvvmhilt.data.api.ModuleSpecificApis;
import com.example.mvvmhilt.data.room.SampleDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SampleRepo_Factory implements Factory<SampleRepo> {
  private final Provider<ModuleSpecificApis> apiProvider;

  private final Provider<SampleDao> daoProvider;

  public SampleRepo_Factory(Provider<ModuleSpecificApis> apiProvider,
      Provider<SampleDao> daoProvider) {
    this.apiProvider = apiProvider;
    this.daoProvider = daoProvider;
  }

  @Override
  public SampleRepo get() {
    return newInstance(apiProvider.get(), daoProvider.get());
  }

  public static SampleRepo_Factory create(Provider<ModuleSpecificApis> apiProvider,
      Provider<SampleDao> daoProvider) {
    return new SampleRepo_Factory(apiProvider, daoProvider);
  }

  public static SampleRepo newInstance(ModuleSpecificApis api, SampleDao dao) {
    return new SampleRepo(api, dao);
  }
}
