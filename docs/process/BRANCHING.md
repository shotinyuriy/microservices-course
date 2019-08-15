# Branching Strategy

## Project On Boarding
0. In the very beginning a developer sets up the local environment.
0. Then they clone a project and create their feature branch.

Every person must have their own feature branch.
It will be used as their main (master) branch.
Create of this branch has to be done once.
```bash
git branch feature/${username}
```

## Working on a task
0. They have a task assigned.
0. Fetch the latest changes from the feature branch. 
0. They create a feature branch for the task.
0. When work is finished push the feature branch to github and 

When one starts working on a task they should create a separate feature branch from their feature branch
Ones should use a task number in the branch name.

```bash
git checkout feature/${username}
git checkout -b feature/${username}/${issueNumber}
```

E.g. ones starts working on an issue number 9, therefore they have to create a branch named **feature/${username}/9**

Once the work is finished a pull-request must be created form the feature branch of the task to the feature branch of the user
```bash
feature/${username}/{$issueNumber} => feature/${username}
```

 