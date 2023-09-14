import styled from '@emotion/styled';
import { Theme } from '../../shared/theme';

export const ModalWrapper = styled.div`
  display: flex;
  text-align: center;
  justify-content: center;
  position: relative;
  align-items: center;
  width: 480px;
  height: 210px;
  border-radius: 5px;
  background-color: ${Theme.primary};
  z-index: 9999;
`

export const ModalContent = styled.div`
  position: relative;
  padding: 20px;
  border-radius: 5px;
  max-width: 400px;
`

export const CloseButton = styled.span`
  position: absolute;
  top: 5px;
  right: 5px;
  cursor: pointer;
`

export const Title = styled.h2`
  margin-top: 0;
`

export const Form = styled.form`
  display: flex;
  flex-direction: column;
`

export const Label = styled.label`
  margin-bottom: 5px;
`

export const Input = styled.input`
  margin-bottom: 10px;
  padding: 5px;
  border-radius: 3px;
  border: 1px solid #ccc;
`

export const Button = styled.button`
  padding: 10px 20px;
  border-radius: 3px;
  background-color: ${Theme.primary2};
  color: #fff;
  border: none;
  cursor: pointer;
`
