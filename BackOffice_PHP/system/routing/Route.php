<?php
namespace FSF\Routing;

use FSF\Routing\Request;
use FSF\Exception;

abstract class Route
{

    /** @var  Request */
    private $request;

    protected $action;
    protected $controller;


    /**
     * @param Request $request
     * @return $this
     */
    public function setRequest(Request $request)
    {
        $this->request = $request;
        return $this;
    }

    /**
     * @return Request
     * @throws Exception
     */
    public function getRequest()
    {
        if (!$this->request) {
            throw new Exception('Route Request not set');
        }
        return $this->request;
    }

    /**
     * Must return true if URI can be handled by route
     * @return bool
     */
    abstract public function canHandle();

    /**
     * @return string
     */
    abstract public function getController();

    /**
     * @return string
     */
    public function getAction()
    {
        return (string) $this->action;
    }

    /**
     * @param string $action
     */
    public function setAction($action)
    {
        $this->action = (string)$action;
    }

    /**
     * Can be used to apply something on request object
     */
    public function handle()
    {

    }

    public function run()
    {
        $actionCalled = $this->getAction();

        $this->getController()->$actionCalled();
    }

    /**
     * Must return true in route has modified request be must be rechecked in dispatch loop
     * WARNING : if this method returns true, please be aware that infinite loop might be triggered
     * @return bool
     */
    public function hasToBeReDispatched()
    {
        return false;
    }
}