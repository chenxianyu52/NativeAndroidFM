# gradle生命周期

## 初始化阶段（root project）
### 执行会settings.gradle脚本

## 配置阶段 (project)
### 执行各个目录下的build.gradle文件

## 执行阶段 (task)
### 只有运行task时，才会在buildFinished的时候执行task
### 如果不是运行task，比如gradle clean -q；那不会运行task中的代码
