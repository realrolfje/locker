#!/usr/bin/env bash
#
# http://alvinalexander.com/git/git-show-change-username-email-address
#

name=$1
email=$2

if [ $# -eq 0 ]; then
    echo "Your current git config:"
    git config --list

    echo
    echo "To change your git user and email info, use:"
    echo "setGitUser.sh 'My Name' 'email@example.com'"
    exit 1
fi

git config user.name "${name}"
git config user.email "${email}"

echo
echo "Your new git configuration:"
git config --list
