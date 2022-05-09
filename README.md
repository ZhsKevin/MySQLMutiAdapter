# MySQLMutiAdapter

### 一个MySQL相关Mapper自动扫描适配器 

- ##### 作用：将Mapper中的mysql语法转化成SQL Server/Oracle/Postgre SQL语法

- ##### 使用方式: java main方法调用执行（目前仅支持window系统）

### 执行整个项目下的mapper文件转换处理

 com.weaver.sqlSyntaxConverter.core.MapperExecutor.execute

   * 执行单个项目下的mapper文件转换处理
   * @param project_filepath  单个项目的根目录,例如:D:\Java\Proj\develop\test\ (注意结尾要带路径分隔符)
   * @param result_filepath 转换结果记录目录,例如:D:\Java\Proj\develop\out\ (注意结尾要带路径分隔符)
   * 转换完成后，使用ideal打开项目根目录 D:\Java\Proj\develop\test\ ， 然后使用idea右上方的git commit 提交比较差异合并

```java
public static void execute(String project_filepath,String result_filepath)
```

### 执行项目根目录下的mapper文件转换处理

com.weaver.sqlSyntaxConverter.core.MapperExecutor.executeMapperConvert

- 检查指定项目根目录下的mapper并转换mapper语法,转换过程结果记录在resultFilePath目录中
- @param projectRootPath  单个或多个项目的上一层目录（根目录）,例如:D:\Java\Proj\develop\test\ (注意结尾要带路径分隔符)
- @param resultFilePath   转换结果记录目录,例如:D:\Java\Proj\develop\out\   (注意结尾要带路径分隔符)
- 
  转换完成后，使用ideal打开根目录下面的具体项目，如 D:\Java\Proj\develop\test\， 然后使用idea右上方的git commit 提交比较差异合并

```java
public static void executeMapperConvert(String projectRootPath,String resultFilePath);
```
