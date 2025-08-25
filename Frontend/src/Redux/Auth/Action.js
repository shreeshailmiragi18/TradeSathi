import axios from "axios";
import * as actionTypes from "./ActionTypes";
import api, { API_BASE_URL } from "@/Api/api";

export const register = (userData) => async (dispatch) => {
  dispatch({ type: actionTypes.REGISTER_REQUEST });
  try {
    const response = await axios.post(`${API_BASE_URL}/auth/signup`, userData);
    const appuser = response.data;
    if (appuser.jwt) localStorage.setItem("jwt", appuser.jwt);
    console.log("registerr :- ", appuser);
    userData.navigate("/");
    dispatch({ type: actionTypes.REGISTER_SUCCESS, payload: appuser.jwt });
  } catch (error) {
    console.log("error ", error);
    dispatch({
      type: actionTypes.REGISTER_FAILURE,
      payload: error.response?.data ? error.response.data : error,
    });
  }
};

export const login = (userData) => async (dispatch) => {
  dispatch({ type: actionTypes.LOGIN_REQUEST });
  try {
    const response = await axios.post(`${API_BASE_URL}/auth/signin`, userData);
    const appuser = response.data;
    if (appuser.twoFactorAuthEnabled) {
      userData.navigate(`/two-factor-auth/${appuser.session}`);
    }
    if (appuser.jwt) {
      localStorage.setItem("jwt", appuser.jwt);
      console.log("login ", appuser);
      userData.navigate("/");
    }
    dispatch({ type: actionTypes.LOGIN_SUCCESS, payload: appuser.jwt });
  } catch (error) {
    console.log("catch error", error);
    dispatch({
      type: actionTypes.LOGIN_FAILURE,
      payload: error.response?.data ? error.response.data : error,
    });
  }
};

export const twoStepVerification =
  ({ otp, session, navigate }) =>
  async (dispatch) => {
    dispatch({ type: actionTypes.LOGIN_TWO_STEP_REQUEST });
    try {
      const response = await axios.post(
        `${API_BASE_URL}/auth/two-factor/otp/${otp}`,
        {},
        {
          params: { id: session },
        }
      );
      const appuser = response.data;

      if (appuser.jwt) {
        localStorage.setItem("jwt", appuser.jwt);
        console.log("login ", appuser);
        navigate("/");
      }
      dispatch({ type: actionTypes.LOGIN_TWO_STEP_SUCCESS, payload: appuser.jwt });
    } catch (error) {
      console.log("catch error", error);
      dispatch({
        type: actionTypes.LOGIN_TWO_STEP_FAILURE,
        payload: error.response?.data ? error.response.data : error,
      });
    }
  };

//  get appuser from token
export const getUser = (token) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.GET_USER_REQUEST });
    try {
      const response = await axios.get(`${API_BASE_URL}/api/appusers/profile`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const appuser = response.data;
      dispatch({ type: actionTypes.GET_USER_SUCCESS, payload: appuser });
      console.log("req Appuser ", appuser);
    } catch (error) {
      const errorMessage = null;
      dispatch({ type: actionTypes.GET_USER_FAILURE, payload: errorMessage });
    }
  };
};

export const sendVerificationOtp = ({ jwt, verificationType }) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.SEND_VERIFICATION_OTP_REQUEST });
    try {
      const response = await api.post(
        `/api/appusers/verification/${verificationType}/send-otp`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );
      const appuser = response.data;
      dispatch({
        type: actionTypes.SEND_VERIFICATION_OTP_SUCCESS,
        payload: appuser,
      });
      console.log("send otp ", appuser);
    } catch (error) {
      console.log("error ", error);
      const errorMessage = error.message;
      dispatch({
        type: actionTypes.SEND_VERIFICATION_OTP_FAILURE,
        payload: errorMessage,
      });
    }
  };
};

export const verifyOtp = ({ jwt, otp }) => {
  console.log("jwt", jwt);
  return async (dispatch) => {
    dispatch({ type: actionTypes.VERIFY_OTP_REQUEST });
    try {
      const response = await api.patch(
        `/api/appusers/verification/verify-otp/${otp}`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );
      const appuser = response.data;
      dispatch({ type: actionTypes.VERIFY_OTP_SUCCESS, payload: appuser });
      console.log("verify otp ", appuser);
    } catch (error) {
      console.log("error ", error);
      const errorMessage = error.message;
      dispatch({ type: actionTypes.VERIFY_OTP_FAILURE, payload: errorMessage });
    }
  };
};

export const enableTwoStepAuthentication = ({ jwt, otp }) => {
  console.log("jwt", jwt);
  return async (dispatch) => {
    dispatch({ type: actionTypes.ENABLE_TWO_STEP_AUTHENTICATION_REQUEST });
    try {
      const response = await api.patch(
        `/api/appusers/enable-two-factor/verify-otp/${otp}`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );
      const appuser = response.data;
      dispatch({
        type: actionTypes.ENABLE_TWO_STEP_AUTHENTICATION_SUCCESS,
        payload: appuser,
      });
      console.log("enable two step authentication ", appuser);
    } catch (error) {
      console.log("error ", error);
      const errorMessage = error.message;
      dispatch({
        type: actionTypes.ENABLE_TWO_STEP_AUTHENTICATION_FAILURE,
        payload: errorMessage,
      });
    }
  };
};

export const sendResetPassowrdOTP = ({
  sendTo,
  verificationType,
  navigate,
}) => {
  console.log("send otp ", sendTo);
  return async (dispatch) => {
    dispatch({ type: actionTypes.SEND_RESET_PASSWORD_OTP_REQUEST });
    try {
      const response = await axios.post(
        `${API_BASE_URL}/auth/appusers/reset-password/send-otp`,
        {
          sendTo,
          verificationType,
        }
      );
      const appuser = response.data;
      navigate(`/reset-password/${appuser.session}`);
      dispatch({
        type: actionTypes.SEND_RESET_PASSWORD_OTP_SUCCESS,
        payload: appuser,
      });
      console.log("otp sent successfully ", appuser);
    } catch (error) {
      console.log("error ", error);
      const errorMessage = error.message;
      dispatch({
        type: actionTypes.SEND_RESET_PASSWORD_OTP_FAILURE,
        payload: errorMessage,
      });
    }
  };
};

export const verifyResetPassowrdOTP = ({
  otp,
  password,
  session,
  navigate,
}) => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.VERIFY_RESET_PASSWORD_OTP_REQUEST });
    try {
      const response = await axios.patch(
        `${API_BASE_URL}/auth/appusers/reset-password/verify-otp`,
        {
          otp,
          password,
        },
        {
          params: {
            id: session,
          },
        }
      );
      const appuser = response.data;
      dispatch({
        type: actionTypes.VERIFY_RESET_PASSWORD_OTP_SUCCESS,
        payload: appuser,
      });
      navigate("/password-update-successfully");
      console.log("VERIFY otp successfully ", appuser);
    } catch (error) {
      console.log("error ", error);
      const errorMessage = error.message;
      dispatch({
        type: actionTypes.VERIFY_RESET_PASSWORD_OTP_FAILURE,
        payload: errorMessage,
      });
    }
  };
};

export const logout = () => {
  return async (dispatch) => {
    dispatch({ type: actionTypes.LOGOUT });
    localStorage.clear();
  };
};
