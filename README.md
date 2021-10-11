# PoC to process registers from database
The purpose with thi poc is understand the Spring Batch configuration to run a batch process. Unit tests wasn't implemented.

### Running the PoC
- Execute docker-compose
```sh
cd docker-compose
docker-compose up
``` 
This will start MySQL. After, connect with a client (like DBeaver) and execute the `create_table.sql` and `insert_person.sql`, both are inside in `resources` folder.

- Build aplication
```sh
./gradlew clean build
``` 
- Execute the job
```sh
./gradlew bootRun
```

The job will retrieve the 15 registers and process by chunks. Each chunk has 3 registers. This chunk size can be configured through property `poc.process.person.chunk.size`

The scheduler was configured to run each 10 seconds.

Full log execution:
```
2021-10-10 22:57:39.785  INFO 65163 --- [           main] c.p.p.batch.PocProcessBatchApplication   : Started PocProcessBatchApplication in 1.264 seconds (JVM running for 1.535)
2021-10-10 22:57:39.786  INFO 65163 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2021-10-10 22:57:39.943  INFO 65163 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=personProcessJob]] launched with the following parameters: [{}]
2021-10-10 22:57:40.003  INFO 65163 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Step already complete or not restartable, so no action to execute: StepExecution: id=2, version=3, name=processPersonStep, status=COMPLETED, exitStatus=COMPLETED, readCount=0, filterCount=0, writeCount=0 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=
2021-10-10 22:57:40.034  INFO 65163 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=personProcessJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 49ms
2021-10-10 22:57:40.163  INFO 65163 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=personProcessJob]] launched with the following parameters: [{time=1633917460001}]
2021-10-10 22:57:40.298  INFO 65163 --- [   scheduling-1] o.s.batch.core.job.SimpleStepHandler     : Executing step: [processPersonStep]
2021-10-10 22:57:40.396  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=1, firstName='nome 1', lastName='last 1', email='email1', age=1}
2021-10-10 22:57:40.398  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=1, firstName='nome 1', lastName='last 1', email='email1@gmail.com', age=1}
2021-10-10 22:57:40.398  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=2, firstName='nome 2', lastName='last 2', email='email2', age=2}
2021-10-10 22:57:40.398  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=2, firstName='nome 2', lastName='last 2', email='email2@gmail.com', age=2}
2021-10-10 22:57:40.398  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=3, firstName='nome 3', lastName='last 3', email='email3', age=3}
2021-10-10 22:57:40.398  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=3, firstName='nome 3', lastName='last 3', email='email3@gmail.com', age=3}
2021-10-10 22:57:40.398  INFO 65163 --- [   scheduling-1] c.poc.process.batch.steps.PersonWriter   : Salving...
2021-10-10 22:57:40.480  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=4, firstName='nome 4', lastName='last 4', email='email4', age=4}
2021-10-10 22:57:40.480  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=4, firstName='nome 4', lastName='last 4', email='email4@gmail.com', age=4}
2021-10-10 22:57:40.480  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=5, firstName='nome 5', lastName='last 5', email='email5', age=5}
2021-10-10 22:57:40.480  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=5, firstName='nome 5', lastName='last 5', email='email5@gmail.com', age=5}
2021-10-10 22:57:40.481  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=6, firstName='nome 6', lastName='last 6', email='email6', age=6}
2021-10-10 22:57:40.481  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=6, firstName='nome 6', lastName='last 6', email='email6@gmail.com', age=6}
2021-10-10 22:57:40.481  INFO 65163 --- [   scheduling-1] c.poc.process.batch.steps.PersonWriter   : Salving...
2021-10-10 22:57:40.548  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=7, firstName='nome 7', lastName='last 7', email='email7', age=7}
2021-10-10 22:57:40.548  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=7, firstName='nome 7', lastName='last 7', email='email7@gmail.com', age=7}
2021-10-10 22:57:40.548  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=8, firstName='nome 8', lastName='last 8', email='email8', age=8}
2021-10-10 22:57:40.548  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=8, firstName='nome 8', lastName='last 8', email='email8@gmail.com', age=8}
2021-10-10 22:57:40.548  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=9, firstName='nome 9', lastName='last 9', email='email9', age=9}
2021-10-10 22:57:40.548  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=9, firstName='nome 9', lastName='last 9', email='email9@gmail.com', age=9}
2021-10-10 22:57:40.548  INFO 65163 --- [   scheduling-1] c.poc.process.batch.steps.PersonWriter   : Salving...
2021-10-10 22:57:40.616  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=10, firstName='nome 10', lastName='last 10', email='email10', age=10}
2021-10-10 22:57:40.616  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=10, firstName='nome 10', lastName='last 10', email='email10@gmail.com', age=10}
2021-10-10 22:57:40.616  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=11, firstName='nome 11', lastName='last 11', email='email11', age=11}
2021-10-10 22:57:40.616  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=11, firstName='nome 11', lastName='last 11', email='email11@gmail.com', age=11}
2021-10-10 22:57:40.616  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=12, firstName='nome 12', lastName='last 12', email='email12', age=12}
2021-10-10 22:57:40.616  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=12, firstName='nome 12', lastName='last 12', email='email12@gmail.com', age=12}
2021-10-10 22:57:40.616  INFO 65163 --- [   scheduling-1] c.poc.process.batch.steps.PersonWriter   : Salving...
2021-10-10 22:57:40.684  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=13, firstName='nome 13', lastName='last 13', email='email13', age=13}
2021-10-10 22:57:40.684  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=13, firstName='nome 13', lastName='last 13', email='email13@gmail.com', age=13}
2021-10-10 22:57:40.684  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=14, firstName='nome 14', lastName='last 14', email='email14', age=14}
2021-10-10 22:57:40.684  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=14, firstName='nome 14', lastName='last 14', email='email14@gmail.com', age=14}
2021-10-10 22:57:40.684  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person: Person{personId=15, firstName='nome 15', lastName='last 15', email='email15', age=15}
2021-10-10 22:57:40.685  INFO 65163 --- [   scheduling-1] c.p.p.batch.steps.PersonItemProcessor    : Person after process: Person{personId=15, firstName='nome 15', lastName='last 15', email='email15@gmail.com', age=15}
2021-10-10 22:57:40.685  INFO 65163 --- [   scheduling-1] c.poc.process.batch.steps.PersonWriter   : Salving...
2021-10-10 22:57:40.846  INFO 65163 --- [   scheduling-1] o.s.batch.core.step.AbstractStep         : Step: [processPersonStep] executed in 548ms
2021-10-10 22:57:40.998  INFO 65163 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=personProcessJob]] completed with the following parameters: [{time=1633917460001}] and the following status: [COMPLETED] in 801ms
2021-10-10 22:57:40.998  INFO 65163 --- [   scheduling-1] c.p.p.batch.config.BatchSchedulerConfig  : Job's Status:::COMPLETED
``` 