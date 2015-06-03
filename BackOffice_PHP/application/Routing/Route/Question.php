<?php

namespace APP\Routing\Route;

use FSF\Routing\Route;

class Question extends Route
{

    /**
     * Must return true if URI can be handled by route
     * @return bool
     */
    public function canHandle()
    {
        $request = $this->getRequest()->getRequestUri();
        if (preg_match("~^/question/(.+)~", $request, $matches)) {
            if (isset($matches[1])) {
                $this->setAction($matches[1]);
                if (method_exists($this->getController(), $this->getAction()))
                    return true;
            }
        }
        return false;
    }

    public function handle()
    {

    }

    /**
     * @return Controller
     */
    public function getController()
    {
        if ($this->controller == null) {
            $this->controller = new \APP\Module\Question\Controller($this->getRequest());
        }

        return $this->controller;
    }
}