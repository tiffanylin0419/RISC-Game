Git and CI Quick Reference
======================================
#### By yitao
## Begin your project with create new feature branch

git clone the repo
use ```git checkout -b NEW_FEATURE_BRANCH``` to
create your new branch ```NEW_FEATURE_BRANCH``` and switch to it


Or just create ```FEATURE_BRANCH```on git lab and 

use ```git checkout FEATURE_BRANCH``` to switch to an existing one


## start coding and testing
do some coding and write testcases for it (following [NSD](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/-/blob/main/nsd.md))

use coverage tool check coverage rate (set ```use trace``` in coverage configuration first time)

suggest you following [gitlabci.pdf](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/-/blob/main/gitlabci.pdf) step4 to debug locally(it's much faster)
debug and make sure everything is ok

## commit and push phase
```git add .```

now you are ready to commit!

you should use ```git commit -m "what you do"```  as usual

but make sure you write what you do clear(what you added and changed)

before you push, use ```git fetch``` to retrieve any new changes 

from the remote repository and update the tracking branch in your local repository

without making any changes to your current working branch.

here is some options for git push


```git push -o ci.skip remote_repo local_branch:remote_branch tag_name```


```-o ci.skip``` you should use it when you didn't change your code
or when going through pipeline is not needed

```remote_repo``` default it should be ```origin```

```local_branch:remote_branch``` default you should enter```FEATURE_BRANCH:FEATURE_BRANCH```

eg: ```testing:testing```, ```main:main```

avoid ```a:b``` to push you feature branch to other branch, please use merge

MAKE SURE YOU KNOW WHAT AND WHERE YOU ARE PUSHING

```tag_name``` it is the commit tag that you assign, runner with this 
tag will run cicd for this commit.
if your tag doesn't match any runners tag, no runner will run pipeline 
for you, and it will always be  stuck. 

You CAN LEAVE IT EMPTY since if the 
commit is no tag, any idle can run it for you. 


if you want to use tag, use ```git tag <tag-name>``` to create new tag
or ```git tag -a <tag-name> -m "tag message"``` to create a tag with
message, than use ```git push --tags``` to update tags in repo


then you should tell the repo owner to add new runner for the tag
(Here I have established ```ece651``` tag and matched runner in repo,
so you can use :```git push master:YOUR_BRANCH ece651```)


eg:
```git push YOUR_BRANCH:YOUR_BRANCH``` the new commit go through the pipeline
and push it with no tag, the former YOUR_BRANCH refers to the branch in your 
local repository that you want to push to the remote repository
while the later YOUR_BRANCH is the name of the branch on the remote repository
that you want to update, any idle runner will handle it

## push wrong file or branch
If you want to remove a commit from the Git history, 

you can use the git revert or git reset commands,

depending on your specific needs and the state of your repository.

```git revert```: This command creates a new commit that 

undoes the changes made in a previous commit, 

effectively removing those changes from the Git history while preserving the commit itself.

This is a safe way to remove a commit without affecting other parts of the Git history.

```git reset```: This command can be used to remove a commit from the Git history, 

but it is a more aggressive approach that can potentially cause data loss. 

There are three different modes of git reset that can be used depending on your needs:

```git reset --soft```: This mode moves the HEAD pointer to a previous commit, 

but leaves the changes made in the subsequent commits in the staging area.

This effectively "undoes" the commits after the specified commit without removing them from the Git history.

```git reset --mixed``` (default): This mode moves the HEAD pointer to a previous commit and 

removes the changes made in the subsequent commits from the staging area. 

This effectively "unstages" the changes, but does not remove them from the Git history.

```git reset --hard```: This mode moves the HEAD pointer to a previous commit and 

completely removes the changes made in the subsequent commits. 

This effectively removes the commits and the changes they made from the Git history. 

This mode should be used with caution, as it can cause data loss if used improperly.

Before using any of these commands, it is important to make a backup of your repository or 

to create a branch to experiment with, in case anything goes wrong.

## Merge
After you have finished your work on a feature branch, 

you can create a merge request for testing

it's actually a commit from source branch to target branch

and after that the source branch will be deleted

any question about git: [funny game to help you](https://learngitbranching.js.org/?demo=&locale=zh_CN)
