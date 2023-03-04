Git and CI Quick Reference
======================================
#### By yitao
## Begin your project with create new feature branch

git clone the repo

use ```git checkout -b NEW_FEATURE_BRANCH``` to
create your new branch ```NEW_FEATURE_BRANCH``` and switch to it


Or just create ```FEATURE_BRANCH```on git lab and 

use ```git switch FEATURE_BRANCH``` to switch to an existing one


## start coding and testing
do some coding and write testcases for it (following [NSD](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/-/blob/main/nsd.md))

use coverage tool check coverage rate (set ```use trace``` in coverage configuration first time)

suggest you following [gitlabci.pdf](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/-/blob/main/gitlabci.pdf) step4 to debug locally(it's much faster)
debug and make sure everything is ok

## commit and push phase

1. ```git add .```

2. ```git commit -m "what you do"``` 

make sure you write what you do clear(what you added and changed)

3. ```git fetch``` + many ```git rebase a b``` download update of remote branch

and merge a and b until there is no conflicts

sometimes you can use```git pull --rebase```（this equals to ```git fetch``` + ```git rebase origin/main```）

any question about git: [funny game to help you](https://learngitbranching.js.org/?demo=&locale=zh_CN)

4. here is some options for git push

```git push -o ci.skip```

```-o ci.skip``` you should use it when you didn't change your code
or when going through pipeline is not needed

still have question? please play the game mentioned above.

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


