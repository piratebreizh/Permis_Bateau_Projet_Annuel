<?php
namespace FSF\Routing;

use FSF\Controller;
use FSF\Request;
use FSF\Exception;
use FSF\Response\Header\ContentType;
use FSF\Session;
use FSF\View;

abstract class Route
{
    /** @var  Session */
    protected $session;

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
     * @return Controller
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
        if($this->needAuthentication() && is_null($this->getSession()->getUser())){
            header('Location: '.\FSF\Module\Authentication\Controller::$authAdress);
        }

        $actionCalled = $this->getAction();

        $actionReturn = $this->getController()->$actionCalled();

        //Considering that if $actionReturn is a View it's HTML else it's a JSON
        $content_type = $actionReturn instanceof View ? ContentType::MIME_TEXT_HTML : ContentType::MIME_JSON;

        $this->getController()->getResponse()
            ->setBody(
                $actionReturn instanceof View ? $actionReturn->render() : $actionReturn
            )
            ->setHeader(new ContentType($content_type))
            ->send();
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

    public function needAuthentication()
    {
        return true;
    }

    /**
     * @return Session
     */
    public function getSession()
    {
        if (is_null($this->session)) {
            $this->session = Session::getInstance();
        }

        return $this->session;
    }

    /**
     * @param Session $session
     * @return Controller
     */
    public function setSession(Session $session)
    {
        $this->session = $session;

        return $this;
    }
}