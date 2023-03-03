Git and CI Quick Reference
======================================
#### By yitao
## Begin your project

create your branch ```YOUR_BRANCH```
git clone the repo

## start coding and testing
do some coding and write testcases for it (following NSD)

use coverage tool check coverage rate

suggest you following [gitlabci.pdf](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/-/blob/main/gitlabci.pdf) step4 to debug locally(it's much faster)
debug and make sure everything is ok

## commit and push phase
now you are ready to commit!

you should use ```git commit -m "what you do"```  as usual
but make sure you write what you do clear(what you added and changed)

here is some options for git push

```git push -o ci.skip remote_repo local_branch:remote_branch tag_name```

```-o ci.skip``` you should use it when you didn't change your code
or when going through pipeline is not needed

```remote_repo``` default it should be ```origin```

```local_branch:remote_branch``` default you should enter```master:YOUR_BRANCH```
MAKE SURE YOU KNOW WHAT AND WHERE YOU ARE PUSHING

```tag_name``` it is the commit tag that you assign, runner with this 
tag will run cicd for this commit.
if your tag doesn't match any runners tag, no runner will run pipeline 
for you, and it will always be  stuck. You CAN LEAVE IT EMPTY since if the 
commit is no tag, any idle can run it for you. 

if you want to use tag, use ```git tag <tag-name>``` to create new tag
or ```git tag -a <tag-name> -m "tag message"``` to create a tag with
message, than use ```git push --tags``` to update tags in repo

then you should tell the repo owner to add new runner for the tag
(Here I have established ```ece651``` tag and matched runner in repo,
so you can use it like ```git push master:YOUR_BRANCH ece651```)

eg:
```git push master:YOUR_BRANCH``` the new commit go through the pipeline
and push it with no tag, the master branch refers to the branch in your 
local repository that you want to push to the remote repository
while YOUR_BRANCH is the name of the branch on the remote repository
that you want to update, any idle runner will handle it

## if push wrong file or branch
you need to roll back with ``` git reset --soft COMMIT_HASH_TO_ROLL_BACK```
then you can push it to recover

## Merge
to do


