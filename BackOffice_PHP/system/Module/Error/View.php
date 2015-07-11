<?php
namespace FSF\Module\Error;

use FSF\View as RootView;

class View extends RootView
{
    static public function getPath()
    {
        return __DIR__.DIRECTORY_SEPARATOR.'View'.DIRECTORY_SEPARATOR;
    }
}